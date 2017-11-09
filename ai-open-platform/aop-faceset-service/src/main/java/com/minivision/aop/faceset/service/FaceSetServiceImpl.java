package com.minivision.aop.faceset.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.minivision.aop.faceset.domain.FaceSet;
import com.minivision.aop.faceset.faceplat.client.FacePlatClient;
import com.minivision.aop.faceset.faceplat.ex.FacePlatException;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.SetCreateResult;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.SetDeleteResult;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.SetDetailResult;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.SetModifyResult;
import com.minivision.aop.faceset.mvc.ex.ServiceException;
import com.minivision.aop.faceset.repository.FaceSetRepository;

import java.io.File;
import java.util.*;


@Service
@Transactional(rollbackFor = Exception.class)
//TODO with redis
public class FaceSetServiceImpl implements FaceSetService {

	private static  final Logger log = LoggerFactory.getLogger(FaceSetServiceImpl.class);

	private final FaceSetRepository faceSetRepository;

	@Autowired
	private FacePlatClient facePlatClient;

	public FaceSetServiceImpl(FaceSetRepository faceSetRepository) {
		this.faceSetRepository = faceSetRepository;
	}

	@Override public List<FaceSet> findAll() {
		List<FaceSet> faceSets = faceSetRepository.findAll();
		return faceSets;
	}

	@Override public List<FaceSet> findByFaceplat() {
		List<FaceSet> faceSets = faceSetRepository.findAll();
		for(FaceSet faceSet: faceSets)
			try {
				SetDetailResult setDetailResult = facePlatClient.getFaceSetDetail(faceSet.getToken());
				if(setDetailResult.getFacesetToken() != null){
					faceSet.setFaceCount(setDetailResult.getFaceCount());
					if(faceSet.getName() == null){
						faceSet.setName(setDetailResult.getDisplayName());
					}
				}
				else  faceSet.setFaceCount(-1);
			} catch (Exception e) {
				log.error("Fail to get Detail for faceset");
				faceSet.setFaceCount(-1);
			}
		return faceSets;
	}

	@Override public FaceSet update(FaceSet faceSet) {
		Assert.notNull(faceSet, "faceSet must not be null");
		Assert.notNull(faceSet.getToken(), "faceSet must not be null");
		FaceSet oldfaceset = faceSetRepository.findOne(faceSet.getToken());
		SetModifyResult modifyResult = facePlatClient.updateFaceset(faceSet);
		if(modifyResult !=null && modifyResult.getFacesetToken() !=null){
			//faceSet.setToken(modifyResult.getFacesetToken());
			faceSet.setCreateTime(oldfaceset.getCreateTime());
			return faceSetRepository.save(faceSet);
		}
		return null;
	}

	public FaceSet create(FaceSet faceSet) {
		Assert.notNull(faceSet, "faceSet must not be null");
		SetCreateResult setCreateResult = facePlatClient.createFaceset(faceSet);
		if(setCreateResult !=null && setCreateResult.getFacesetToken() !=null){
			faceSet.setToken(setCreateResult.getFacesetToken());
			return faceSetRepository.save(faceSet);
		}
		return null;
	}

	@Override public FaceSet find(String token) {
		Assert.notNull(token, "token must not be null");
		return faceSetRepository.findOne(token);
	}

	@Override public void delete(String token) throws ServiceException {
		Assert.notNull(token, "token must not be null");
		FaceSet faceSet = faceSetRepository.findOne(token);
		faceSetRepository.delete(token);
		try {
			SetDeleteResult deleteResult = facePlatClient.delFaceset(token, true);
			if(deleteResult ==null || deleteResult.getFacesetToken() ==null){
				throw new ServiceException("fail to delete faceset on redis");
			}
		}catch (FacePlatException e){
			if(!e.getMessage().contains("NOT_EXIST")){
				throw new ServiceException("fail to delete faceset on redis");
			}

		}

	}

	@Override public Set<FaceSet> findAll(String ids) {
		Iterable<FaceSet> iterable = faceSetRepository.findAll(Arrays.asList(ids.split(",")));
		Set<FaceSet> set = new HashSet<FaceSet>();
		if (iterable != null) {
			Iterator<FaceSet> iterator = iterable.iterator();
			while (iterator.hasNext()) {
				set.add((FaceSet) iterator.next());
			}
		}
		return set;
	}

	@Override public Page<FaceSet> findAll(int page,int size) {
		Pageable pageable = new PageRequest(page,size);
		return faceSetRepository.findAll(pageable);
	}

	@Override public List<File> getSubFile(String filepath) {
		File[] files = null;
		if("".equals(filepath)) {
			files = Arrays.stream(File.listRoots()).filter(file -> file.canRead()).toArray(File[]::new);
		}
		else{
			files = new File(filepath).listFiles(childfile -> childfile.isDirectory()&& !childfile.isHidden());
		}
		return files !=null ?Arrays.asList(files):new ArrayList<>();
	}

}
