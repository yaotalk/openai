package com.minivision.aop.face.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.minivision.aop.face.entity.Face;
import com.minivision.aop.face.entity.FaceSet;
import com.minivision.aop.face.repository.FaceSetRepository;
import com.minivision.aop.face.rest.result.FailureDetail;
import com.minivision.aop.face.rest.result.faceset.AddFaceResult;
import com.minivision.aop.face.rest.result.faceset.RemoveFaceResult;
import com.minivision.aop.face.rest.result.faceset.SetCreateResult;
import com.minivision.aop.face.rest.result.faceset.SetDeleteResult;
import com.minivision.aop.face.rest.result.faceset.SetDetailResult;
import com.minivision.aop.face.rest.result.faceset.SetListResult;
import com.minivision.aop.face.rest.result.faceset.SetMergeResult;
import com.minivision.aop.face.rest.result.faceset.SetModifyResult;
import com.minivision.aop.face.service.ex.ErrorType;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.minivision.aop.face.util.ChunkRequest;

@Transactional
@Service
public class FaceSetServiceRedis implements FaceSetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FaceSetServiceRedis.class);

	@Autowired
	private FaceSetRepository setRepository;

	//@Autowired
	//private FaceRepository faceRepository;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private FaceCommonService commonService;

	/**
	 * 创建人脸库
	 */
	public SetCreateResult create(String owner, String outerId, String displayName) {
		return create(owner, outerId, displayName, 1000);
	}
	
	public SetCreateResult create(String owner, String outerId, String displayName, int capacity){
	  LOGGER.info("create faceset at service");
      FaceSet set = new FaceSet();
      set.setOwner(owner);
      set.setOuterId(outerId);
      set.setDisplayName(displayName);
      set.setCapacity(capacity);
      setRepository.save(set);

      SetCreateResult result = new SetCreateResult();
      result.setFacesetToken(set.getFacesetToken());
      result.setOuterId(outerId);
      return result;
	}

	/**
	 * 添加人脸
	 */
	@CacheEvict(cacheNames="features", key="#setToken")
	public AddFaceResult addFace(String setToken, String... faceTokens) throws FacePlatException {
		LOGGER.info("add faces at service");
		FaceSet set = commonService.findOneFaceset(setToken);

		int capacity = set.getCapacity();
		int count = stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken)).intValue();
		if (capacity - count < faceTokens.length) {
			LOGGER.error("beyond the capacity of faceset:[{}]", setToken);
			throw new FacePlatException(ErrorType.FACESET_OUT_CAPACITY);
		}

		List<FailureDetail> failureDetail = new ArrayList<>();
		int faceAdded = 0;
		for (String faceToken : faceTokens) {
			try {
				addFace(setToken, faceToken);
				faceAdded++;
			} catch (FacePlatException e) {
				FailureDetail failure = new FailureDetail(faceToken, e.getMessage());
				failureDetail.add(failure);
			}
		}

		AddFaceResult result = new AddFaceResult();
		result.setFacesetToken(set.getFacesetToken());
		result.setOuterId(set.getOuterId());
		result.setFaceCount(stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken)).intValue());
		result.setFaceAdded(faceAdded);
		if (failureDetail.size() > 0) {
			result.setFailureDetail(failureDetail);
		}
		return result;
	}

	/**
	 * 添加一张人脸
	 * 
	 * @param setToken
	 * @param faceToken
	 * @throws FacePlatException
	 */
	private void addFace(String setToken, String faceToken) throws FacePlatException {
		Face one = commonService.findOneFaceFromRedis(faceToken);
		if (one == null) {
			one = commonService.findOneFaceFromCache(faceToken);
			if(one == null){
			  throw new FacePlatException(ErrorType.FACE_NOT_EXIST);
			}
			/*if(saveImage && one.getImg() != null){
			    File file = commonService.getFaceImg(faceToken);
	            try {
	                FileUtils.writeByteArrayToFile(file, one.getImg());
	            } catch (IOException e) {
	                throw new FacePlatException(ErrorType.SERVER_ERROR);
	            }
			}*/
			// save in redis when the face added to faceset
			//faceRepository.save(one);
			commonService.save(one);
		}

		String key = commonService.getFaceSetKey(setToken);
		if (stringRedisTemplate.opsForZSet().rank(key, faceToken) != null) {
			throw new FacePlatException(ErrorType.FACE_ADD_REPEATED);
		}
		stringRedisTemplate.opsForZSet().add(key, faceToken, 0);
		commonService.addReferenceCnt(one.getToken());
		//stringRedisTemplate.opsForHash().increment("faces:" + one.getToken(), "referenceCnt", 1);
	}

	/**
	 * 删除人脸库
	 */
	@Override
	@CacheEvict(cacheNames="features", key="#setToken")
	public SetDeleteResult delete(String setToken, boolean force) throws FacePlatException {
		LOGGER.info("delete faceset service");
		FaceSet faceset = commonService.findOneFaceset(setToken);

		if (force) {
			delete(setToken);
		} else {
			if (stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken)).intValue() > 0) {
				throw new FacePlatException(ErrorType.FACESET_NOT_NULL);
			} else {
				delete(setToken);
			}
		}

		SetDeleteResult result = new SetDeleteResult();
		result.setFacesetToken(setToken);
		result.setOuterId(faceset.getOuterId());
		return result;
	}

	private void delete(String setToken) {

		Set<String> faceTokens = stringRedisTemplate.opsForZSet().rangeByScore(commonService.getFaceSetKey(setToken), 0, 0);
		if (faceTokens != null && !faceTokens.isEmpty()) {
			for (String faceToken : faceTokens) {
				try {
					removeFace(setToken, faceToken);
				} catch (FacePlatException e) {
				}
			}
		}

		stringRedisTemplate.opsForZSet().removeRangeByScore(commonService.getFaceSetKey(setToken), 0, 0);
		setRepository.delete(setToken);
	}

	/**
	 * 删除人脸
	 */
	@Override
	@CacheEvict(cacheNames="features", key="#setToken")
	public RemoveFaceResult removeFace(String setToken, String... faceTokens) throws FacePlatException {
		LOGGER.info("remove face service");
		FaceSet set = commonService.findOneFaceset(setToken);

		List<FailureDetail> failureDetail = new ArrayList<>();
		int faceremoved = 0;
		for (String faceToken : faceTokens) {
			try {
				removeFace(setToken, faceToken);
				faceremoved++;
			} catch (FacePlatException e) {
				FailureDetail failure = new FailureDetail(faceToken, e.getMessage());
				failureDetail.add(failure);
			}
		}

		RemoveFaceResult result = new RemoveFaceResult();
		result.setFacesetToken(set.getFacesetToken());
		result.setOuterId(set.getOuterId());
		result.setFaceCount(stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken)).intValue());
		result.setFaceRemoved(faceremoved);
		if (failureDetail.size() > 0) {
			result.setFailureDetail(failureDetail);
		}
		return result;
	}

	private void removeFace(String setToken, String faceToken) throws FacePlatException {
		Face one = commonService.findOneFaceFromRedis(faceToken);

		String key = commonService.getFaceSetKey(setToken);
		Long removed = stringRedisTemplate.opsForZSet().remove(key, faceToken);
		if (removed == null || removed == 0) {
			throw new FacePlatException(ErrorType.FACE_NOT_EXIST);
		}
		
		int referenceCnt = commonService.decreaseReferenceCnt(faceToken);
		if (referenceCnt <= 0 && one != null) {
		  commonService.delete(one);
          /*if (removeImage) {
              FileUtils.deleteQuietly(commonService.getFaceImg(faceToken));
          }*/
		}
	}

	/**
	 * 修改人脸库
	 * 
	 * @throws FacePlatException
	 */
	public SetModifyResult modify(String setToken, String displayName, int capacity) throws FacePlatException {
		LOGGER.info("mofidy faceset service");

		FaceSet faceSet = commonService.findOneFaceset(setToken);
		faceSet.setDisplayName(displayName);
		if(capacity > 0){
		  faceSet.setCapacity(capacity);
		}
		setRepository.save(faceSet);

		SetModifyResult result = new SetModifyResult();
		result.setFacesetToken(setToken);
		result.setOuterId(faceSet.getOuterId());
		return result;
	}

	/**
	 * 查看人脸库信息
	 */
	@Override
	public SetDetailResult getFaceSetDetail(String setToken, long offset, long count) throws FacePlatException {
		LOGGER.info("get faceset detail message service");

		FaceSet faceset = commonService.findOneFaceset(setToken);
		if (faceset == null) {
			throw new FacePlatException(ErrorType.FACESET_NOT_EXIST);
		}

		SetDetailResult result = new SetDetailResult();
		result.setFacesetToken(faceset.getFacesetToken());
		result.setOuterId(faceset.getOuterId());
		result.setDisplayName(faceset.getDisplayName());
		result.setFaceCount(stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken)).intValue());
		result.setCapacity(faceset.getCapacity());
		
		if(count>0){
          Set<String> faceTokens = stringRedisTemplate.opsForZSet().range(commonService.getFaceSetKey(setToken), offset,
              offset + count - 1);
          result.setFaceTokens(new ArrayList<String>(faceTokens));
        }

		return result;
	}

	/**
	 * 合并人脸库
	 */
	@Override
	@CacheEvict(cacheNames="features", key="#setToken1")
	public SetMergeResult mergeFace(String setToken1, String setToken2) throws FacePlatException {
		FaceSet faceset1 = commonService.findOneFaceset(setToken1);
		if (faceset1 == null) {
			throw new FacePlatException(ErrorType.FACESET_NOT_EXIST);
		}

		FaceSet faceset2 = commonService.findOneFaceset(setToken2);
		if (faceset2 == null) {
			throw new FacePlatException(ErrorType.FACESET_NOT_EXIST);
		}

		int capacity = faceset1.getCapacity();
		int count1 = stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken1)).intValue();
		int count2 = stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken2)).intValue();
		if (capacity - count1 < count2) {
			LOGGER.error("beyond the capacity of faceset:[{}]", setToken1);
			throw new FacePlatException(ErrorType.FACESET_OUT_CAPACITY);
		}

		Set<String> faceTokens = stringRedisTemplate.opsForZSet().rangeByScore(commonService.getFaceSetKey(setToken2),
				0, 0);

		if (faceTokens != null && !faceTokens.isEmpty()) {
			for (String faceToken : faceTokens) {
				String key = commonService.getFaceSetKey(setToken1);
				if (stringRedisTemplate.opsForZSet().rank(key, faceToken) == null) {
					stringRedisTemplate.opsForZSet().add(key, faceToken, 0);
				} else {
					stringRedisTemplate.opsForHash().increment("faces:" + faceToken, "referenceCnt", -1);
				}
			}
		}

		setRepository.delete(setToken2);

		SetMergeResult result = new SetMergeResult();
		result.setFacesetToken(faceset1.getFacesetToken());
		result.setOuterId(faceset1.getOuterId());
		result.setDisplayName(faceset1.getDisplayName());
		result.setFaceCount(stringRedisTemplate.opsForZSet().size(commonService.getFaceSetKey(setToken1)).intValue());
		result.setCapacity(faceset1.getCapacity());

		return result;
	}

  @Override
  public SetListResult getFaceSetList(int offset, int count) throws FacePlatException {
    ChunkRequest chunk = new ChunkRequest(offset, count);
    Page<FaceSet> page = setRepository.findAll(chunk);
    SetListResult result = new SetListResult();
    result.setTotal(page.getTotalElements());
    result.setFaceSets(page.getContent());
    return result;
  }

}
