package com.minivision.aop.face.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.minivision.aop.face.entity.Face;
import com.minivision.aop.face.rest.result.detect.CompareResult;
import com.minivision.aop.face.rest.result.detect.DetectedFace;
import com.minivision.aop.face.rest.result.detect.DetectedFace.Rectangle;
import com.minivision.aop.face.rest.result.detect.FaceAttribute;
import com.minivision.aop.face.rest.result.detect.SearchResult;
import com.minivision.aop.face.rest.result.detect.SearchResult.Result;
import com.minivision.aop.face.service.ex.ErrorType;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.minivision.aop.face.thrift.FaceFeatures;
import com.minivision.aop.face.thrift.FaceInfo;
import com.minivision.aop.face.thrift.ImageData;
import com.minivision.aop.face.thrift.Serv.Iface;
import com.minivision.aop.face.thrift.pool.ThriftServiceClientProxyFactory;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Transactional
@Service
@ConfigurationProperties("algorithm.param")
public class FaceServiceThrift extends BaseService implements FaceService {
  
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceServiceThrift.class);
  
	@Autowired
	private Cache faceCache;

	@Autowired
	private ThriftServiceClientProxyFactory imageService;

	@Autowired
	private FaceCommonService commonService;

	private double[] scoreLevels;

	public double[] getScoreLevels() {
		return scoreLevels;
	}

	public void setScoreLevels(double[] scoreLevels) {
		this.scoreLevels = scoreLevels;
	}
	
	public List<DetectedFace> detectFallback(byte[] img, boolean attributes, Throwable e) {
	  LOGGER.error("face detect exception", e);
	  return new ArrayList<>();
	}
	
	/**
     * 人脸检测
     */
	@HystrixCommand(threadPoolKey = "thriftPool", fallbackMethod = "detectFallback")
    @Override
    public List<DetectedFace> detect(byte[] img, boolean attributes) throws FacePlatException {
        Iface client = (Iface) imageService.getObject();
        ImageData tr = new ImageData();
        tr.setImgdata(img);
        tr.setUse_face_features(true);
        tr.setUse_age_attribute(attributes);
        tr.setUse_gender_attribute(attributes);
        
        try {
            FaceInfo faceInfo = client.getFeatures(tr);
            List<FaceFeatures> faceFeatures = faceInfo.getFaceFeatures();
            List<DetectedFace> result = new ArrayList<>();
            for (FaceFeatures reIDFeature : faceFeatures) {
                DetectedFace detectedFace = new DetectedFace();
                Rectangle rectangle = detectedFace.new Rectangle();
                //List<Integer> bbox = reIDFeature.getBbox();
                //assert bbox.size() == 4;
                rectangle.setLeft(reIDFeature.getFaceRectLeft());
                rectangle.setTop(reIDFeature.getFaceRectTop());
                rectangle.setWidth(reIDFeature.getFaceRectWidth());
                rectangle.setHeight(reIDFeature.getFaceRectHeight());
                detectedFace.setFaceRectangle(rectangle);

                List<Double> features = reIDFeature.getFeatures();
                float[] featureArray = new float[features.size()];
                for (int i = 0; i < features.size(); i++) {
                    featureArray[i] = (float) features.get(i).doubleValue();
                }
                detectedFace.setFeature(featureArray);

                Face face = new Face();
                face.setFeature(featureArray);
                face.setToken(UUID.randomUUID().toString());
                
                if (attributes) {
                  int age = reIDFeature.getAge();
                  double confidenceAge = reIDFeature.getConfidence_age();
                  int gender = reIDFeature.getGender();
                  double confidenceGender = reIDFeature.getConfidence_gender();
                  FaceAttribute fa = new FaceAttribute();
                  fa.setAge(age);
                  fa.setAgeConfidence(confidenceAge);
                  fa.setGender(gender);
                  fa.setGenderConfidence(confidenceGender);
                  detectedFace.setFaceAttribute(fa);
                }
                
                //if(saveImage){
                  //BufferedImage bi = ImageUtils.getBufferedImage(img);
                  //This image format (Jpeg-Custom) cannot be written.
                  //face.setImg(Imaging.writeImageToBytes(subimage, ImageFormats.PNG, null)); 
                  //BufferedImage subimage = bi.getSubimage(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(),rectangle.getHeight());
                  //face.setImg(ImageUtils.writeImageToBytes(subimage, "png"));
                  
                  //TODO save in FastDFS
                //}
                
                // save in cache ,not redis
                // faceRepository.save(face);
                faceCache.putIfAbsent(face.getToken(), face);
                detectedFace.setFaceToken(face.getToken());

                result.add(detectedFace);
            }
            return result;
        } catch (TException e) {
            throw new FacePlatException(ErrorType.FACE_ALGO_ERROR, e);
        } 
    }

	/**
	 * 人脸检测
	 */
	@Override
	public List<DetectedFace> detect(byte[] img) throws FacePlatException {
	  return detect(img, false);
	}
	
	public List<DetectedFace> getFaceAttributeFallback(byte[] img, Throwable e) {
	  LOGGER.error("getFaceAttribute exception", e);
	  return new ArrayList<>();
	}
	
	@HystrixCommand(threadPoolKey = "thriftPool", fallbackMethod = "getFaceAttributeFallback")
	@Override
	public List<DetectedFace> getFaceAttribute(byte[] img) throws FacePlatException {
      LOGGER.info("get face attr at service");
      Iface client = (Iface) imageService.getObject();
      ImageData tr = new ImageData();
      tr.setImgdata(img);
      tr.setUse_face_features(false);
      tr.setUse_age_attribute(true);
      tr.setUse_gender_attribute(true);
      
      try {
        FaceInfo faceInfo = client.getFeatures(tr);
        
        List<DetectedFace> faList = new ArrayList<>();
        List<FaceFeatures> faceFeatures = faceInfo.getFaceFeatures();
        for (FaceFeatures reIDFeature : faceFeatures) {
          DetectedFace detectedFace = new DetectedFace();
          Rectangle rectangle = detectedFace.new Rectangle();
          rectangle.setLeft(reIDFeature.getFaceRectLeft());
          rectangle.setTop(reIDFeature.getFaceRectTop());
          rectangle.setWidth(reIDFeature.getFaceRectWidth());
          rectangle.setHeight(reIDFeature.getFaceRectHeight());
          detectedFace.setFaceRectangle(rectangle);
          int age = reIDFeature.getAge();
          double confidenceAge = reIDFeature.getConfidence_age();
          int gender = reIDFeature.getGender();
          double confidenceGender = reIDFeature.getConfidence_gender();
          FaceAttribute fa = new FaceAttribute();
          fa.setAge(age);
          fa.setAgeConfidence(confidenceAge);
          fa.setGender(gender);
          fa.setGenderConfidence(confidenceGender);
          detectedFace.setFaceAttribute(fa);
          faList.add(detectedFace);
        }
        return faList;
      } catch (TException e) {
        throw new FacePlatException(ErrorType.FACE_ALGO_ERROR, e);
      }
	}
	
	@Override
	public CompareResult compare(String faceToken1, String faceToken2, byte[] img1, byte[] img2)
			throws FacePlatException {
	    Assert.isTrue(faceToken1 != null || img1 != null, "facetoken1 and img1 cannot be empty at the same time");
	    Assert.isTrue(faceToken2 != null || img2 != null, "facetoken2 and img2 cannot be empty at the same time");
	  
	    CompareResult result = new CompareResult();
	  
	    float[] feature1;  
	    if(!StringUtils.isEmpty(faceToken1)){
	      Face face1 = commonService.findOneFace(faceToken1);
	      if(face1 == null){
            throw new FacePlatException(ErrorType.FACE_NOT_EXIST);
          }
	      feature1 = face1.getFeature();
	    }else{
	      List<DetectedFace> detectedFaceList = detect(img1);
          if(detectedFaceList.isEmpty()){
            throw new FacePlatException(ErrorType.NO_FACE_DETECTED);
          }
          result.setFaces1(detectedFaceList);
          DetectedFace detectedFace = detectedFaceList.get(0);
          feature1 = detectedFace.getFeature();
	    }
	    
	    float[] feature2;  
        if(!StringUtils.isEmpty(faceToken2)){
          Face face2 = commonService.findOneFace(faceToken2);
          if(face2 == null){
            throw new FacePlatException(ErrorType.FACE_NOT_EXIST);
          }
          feature2 = face2.getFeature();
        }else{
          List<DetectedFace> detectedFaceList = detect(img2);
          if(detectedFaceList.isEmpty()){
            throw new FacePlatException(ErrorType.NO_FACE_DETECTED);
          }
          result.setFaces2(detectedFaceList);
          DetectedFace detectedFace = detectedFaceList.get(0);
          feature2 = detectedFace.getFeature();
        }
        
        result.setConfidence(getScore(calDist(feature1, feature2)));
        return result;
	}
	
	@Override
	public SearchResult search(String faceToken, byte[] img, String facesetToken, int count) throws FacePlatException {
		SearchResult searchResult = new SearchResult();
		float[] feature = {};
		if (!StringUtils.isEmpty(faceToken)) {
			Face face = commonService.findOneFace(faceToken);
			if(face == null){
	           throw new FacePlatException(ErrorType.FACE_NOT_EXIST);
	        }
			feature = face.getFeature();
		} else {
			List<DetectedFace> detectedFaceList = detect(img);
			searchResult.setFaces(detectedFaceList);
			if(detectedFaceList.isEmpty()){
			  throw new FacePlatException(ErrorType.NO_FACE_DETECTED);
			}
			// 第一个人脸进行人脸搜索
			DetectedFace detectedFace = detectedFaceList.get(0);
			feature = detectedFace.getFeature();
			faceToken = detectedFace.getFaceToken();
			
		}
		//Set<String> faceTokens = stringRedisTemplate.opsForZSet().rangeByScore(commonService.getFaceSetKey(facesetToken), 0, 0);
		//List<float[]> allFeatures = commonService.getAllFeatures(faceTokens);
		
		Map<String, float[]> features = commonService.getAllFeaturesOfFaceSet(facesetToken);
		List<Result> tempResults = new LinkedList<>();
		for(Entry<String, float[]> entry: features.entrySet()){
		  float[] f = entry.getValue();
		  double dist = calDist(feature, f);
		  double score = getScore(dist);
		  Result result = searchResult.new Result();
          result.setFaceToken(entry.getKey());
          result.setConfidence(score);
		  if(tempResults.size() < count){
            tempResults.add(result);
		  }else if(score > tempResults.get(count-1).getConfidence()){
            tempResults.set(count-1, result);
		  }
		  tempResults.sort((e1, e2) -> (e1.getConfidence() < e2.getConfidence() ? 1 : -1));
		}
		List<Result> collect = tempResults.stream().sorted((e1, e2) -> (e1.getConfidence() < e2.getConfidence() ? 1 : -1)).limit(count)
				.collect(Collectors.toList());
		searchResult.setResults(collect);
		searchResult.setFaceToken(faceToken);
		return searchResult;
	}

	// 计算N维向量的欧式距离的平方
	private strictfp double calDist(float[] faceFutureArray1, float[] faceFutureArray2) {
	    Assert.isTrue(faceFutureArray1.length == faceFutureArray2.length, "feature length different, maybe face algorithmic model had changed");
		double confidence = 0;
		for (int i = 0; i < faceFutureArray1.length; i++) {
			double faceFuture1 = faceFutureArray1[i];
			double faceFuture2 = faceFutureArray2[i];
			confidence += Math.pow(faceFuture1 - faceFuture2, 2);
		}
		//return Math.sqrt(confidence);
		return confidence;
	}

	private strictfp double getScore(double dist) {
		float score = 0;
		if(dist < scoreLevels[0] * 0.333){
		    score = 1;
		} else if (dist < scoreLevels[0]) {
			score = (float) (1.5*(scoreLevels[0] - dist) / scoreLevels[0] * 0.2 + 0.8);
		} else if ((dist >= scoreLevels[0]) && (dist <= scoreLevels[1])) {
			score = (float) (0.8 - (dist - scoreLevels[0]) / ((scoreLevels[1] - scoreLevels[0])) * 0.2);
		} else if ((dist > scoreLevels[1]) && (dist < scoreLevels[2])) {
			score = (float) ((scoreLevels[2] - dist) / (scoreLevels[2] - scoreLevels[1]) * 0.6);
		} else if (dist > scoreLevels[2]) {
			score = 0;
		}
		return (score <= 1.0 ? score : 1.0);
	}

}
