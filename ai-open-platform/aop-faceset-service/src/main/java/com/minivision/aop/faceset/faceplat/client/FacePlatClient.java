package com.minivision.aop.faceset.faceplat.client;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minivision.aop.faceset.domain.FaceSet;
import com.minivision.aop.faceset.faceplat.ex.FacePlatException;
import com.minivision.aop.faceset.faceplat.result.RestResult;
import com.minivision.aop.faceset.faceplat.result.detect.CompareResult;
import com.minivision.aop.faceset.faceplat.result.detect.DetectResult;
import com.minivision.aop.faceset.faceplat.result.detect.SearchResult;
import com.minivision.aop.faceset.faceplat.result.detect.faceset.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
//TODO Excepton
@Component
public class FacePlatClient {
  @Autowired
  private RestTemplate restClient;
  @Value(value = "${faceservice.basePath}")
  private String basePath;
  
  @Autowired
  private ObjectMapper mapper;
  
  public String getBasePath() {
    return basePath;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  private LinkedMultiValueMap<String,Object> bulidParams(){
    LinkedMultiValueMap<String,Object> params = new LinkedMultiValueMap<String,Object>();
    return params;
  }
  
  private <T> RestResult<T> postForResult(String path, LinkedMultiValueMap<String,Object> params, Class<T> resultType) throws FacePlatException{
    try {
      String res = restClient.postForObject(path, params, String.class);
      
      JavaType javaType = mapper.getTypeFactory().constructParametricType(RestResult.class, resultType);
      
      RestResult<T> readValue = mapper.readValue(res, javaType);
      if(readValue.getStatus() != 0){
        throw new FacePlatException(readValue.getStatus(), readValue.getErrorMessage());
      }
      return readValue;
      
    } catch (HttpServerErrorException e){
      throw new FacePlatException(e.getRawStatusCode(), e.getMessage());
    } catch (Exception e) {
      throw new FacePlatException(500, e.getMessage());
    }
  }
  
  // --------face detect API------------
  
  /**
   * 1:1 compare
   * @param faceToken1
   * @param faceToken2
   * @return
   * @throws FacePlatException 
   */
  public CompareResult compare(String faceToken1, String faceToken2) throws FacePlatException{
    return compareBase(faceToken1, faceToken2).getData();
  }
  
  @Deprecated
  public RestResult<CompareResult> compareBase(String faceToken1, String faceToken2) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("faceToken1", faceToken1);
    params.add("faceToken2", faceToken2);
    return postForResult(basePath+"/api/v1/compare", params, CompareResult.class);
  }
  
  
  public DetectResult detect(byte[] image) throws FacePlatException{
    return detectBase(image).getData();
  }
  
  @Deprecated
  public RestResult<DetectResult> detectBase(byte[] image) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    ByteArrayResource arrayResource = new ByteArrayResource(image){ 
      @Override
      public String getFilename() throws IllegalStateException { 
          return "temp.jpg";
      }
    }; 
    params.add("imageFile", arrayResource);
    return postForResult(basePath+"/api/v1/detect", params, DetectResult.class);
  }
  
  
  // 1:N
  public SearchResult search(String faceSetToken, String faceToken) throws FacePlatException{
    return search(faceSetToken, faceToken, 1);
  }
  
  public SearchResult search(String faceSetToken, String faceToken, int TopN) throws FacePlatException{
    return searchBase(faceSetToken, faceToken, TopN).getData();
  }
  @Deprecated
  public RestResult<SearchResult> searchBase(String faceSetToken, String faceToken, int TopN) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken", faceSetToken);
    params.add("faceToken", faceToken);
    params.add("resultCount", TopN);
    return postForResult(basePath+"/api/v1/search", params, SearchResult.class);
  }
  
  public SearchResult search(String faceSetToken, byte[] image) throws FacePlatException{
    return search(faceSetToken, image, 1);
  }
  
  
  public SearchResult search(String faceSetToken, byte[] image, int TopN) throws FacePlatException{
    return searchBase(faceSetToken, image, TopN).getData();
  }
  
  @Deprecated
  public RestResult<SearchResult> searchBase(String faceSetToken, byte[] image, int TopN) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken", faceSetToken);
    ByteArrayResource arrayResource = new ByteArrayResource(image){ 
      @Override
      public String getFilename() throws IllegalStateException { 
          return "temp.jpg";
      }
    }; 
    params.add("imageFile", arrayResource);
    params.add("resultCount", TopN);
    return postForResult(basePath+"/api/v1/search", params, SearchResult.class);
  }
  
  //----- faceset API ----
  public SetCreateResult createFaceset(FaceSet faceSet) throws FacePlatException{
    return createFacesetBase(faceSet).getData();
  }

  public SetModifyResult updateFaceset(FaceSet faceSet) throws FacePlatException{
    return updateFacesetBase(faceSet).getData();
  }

  @Deprecated
  public RestResult<SetModifyResult> updateFacesetBase(FaceSet faceSet) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken",faceSet.getToken());
    params.add("displayName",faceSet.getName());
    params.add("capacity",faceSet.getCapacity());
    return postForResult(basePath+"/api/v1/faceset/modify", params, SetModifyResult.class);
  }

  @Deprecated
  public RestResult<SetCreateResult> createFacesetBase(FaceSet faceSet) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("displayName",faceSet.getName());
    params.add("capacity",faceSet.getCapacity());
    return postForResult(basePath+"/api/v1/faceset/create", params, SetCreateResult.class);
  }

  public SetDeleteResult delFaceset(String facesetToken,boolean force) throws FacePlatException{
    return delFacesetBase(facesetToken, force).getData();
  }
  
  @Deprecated
  public RestResult<SetDeleteResult> delFacesetBase(String facesetToken,boolean force) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken",facesetToken);
    params.add("force",force);
    return postForResult(basePath+"/api/v1/faceset/delete",params,SetDeleteResult.class);
  }

  public AddFaceResult addFace(String facesetToken, List<String> faceTokens) throws FacePlatException{
    return addFaceBase(facesetToken, faceTokens).getData();
  }
  
  public AddFaceResult addFace(String facesetToken, String... faceTokens) throws FacePlatException{
    return addFace(facesetToken, Arrays.asList(faceTokens));
  }
  
  @Deprecated
  public RestResult<AddFaceResult> addFaceBase(String facesetToken, List<String> faceTokens) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken", facesetToken);
    params.add("faceTokens", StringUtils.join(faceTokens,','));
    return postForResult(basePath+"/api/v1/faceset/addface", params, AddFaceResult.class);
  }
  

  public SetDetailResult getFaceSetDetail(String facesetToken) throws FacePlatException{
    return getFaceSetDetailBase(facesetToken).getData();
  }
  
  @Deprecated
  public RestResult<SetDetailResult> getFaceSetDetailBase(String facesetToken) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken", facesetToken);
    return postForResult(basePath+"/api/v1/faceset/getdetail", params, SetDetailResult.class);
  }

  public RemoveFaceResult removeFace(String facesetToken,String faceTokens) throws FacePlatException{
    return removeFaceBase(facesetToken, faceTokens).getData();
  }
  
  @Deprecated
  public RestResult<RemoveFaceResult> removeFaceBase(String facesetToken,String faceTokens) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("facesetToken", facesetToken);
    params.add("faceTokens", faceTokens);
    return postForResult(basePath+"/api/v1/faceset/removeface", params, RemoveFaceResult.class);
  }
  
  public SetListResult faceList(int offset, int limit) throws FacePlatException{
    return faceListBase(offset, limit).getData();
  }
  
  @Deprecated
  public RestResult<SetListResult> faceListBase(int offset, int limit) throws FacePlatException{
    LinkedMultiValueMap<String,Object> params = bulidParams();
    params.add("offset", offset);
    params.add("limit", limit);
    return postForResult(basePath+"/api/v1/faceset/list", params, SetListResult.class);
  }

}
