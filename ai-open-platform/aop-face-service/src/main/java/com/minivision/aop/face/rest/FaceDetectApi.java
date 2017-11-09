package com.minivision.aop.face.rest;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.minivision.ai.rest.result.RestResult;
import com.minivision.aop.face.rest.param.detect.CompareParam;
import com.minivision.aop.face.rest.param.detect.DetectParam;
import com.minivision.aop.face.rest.param.detect.SearchParam;
import com.minivision.aop.face.rest.result.detect.CompareResult;
import com.minivision.aop.face.rest.result.detect.DetectResult;
import com.minivision.aop.face.rest.result.detect.DetectedFace;
import com.minivision.aop.face.rest.result.detect.SearchResult;
import com.minivision.aop.face.service.FaceService;
import com.minivision.aop.face.service.ex.FacePlatException;
import com.minivision.aop.face.util.ImageUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/v1", method = RequestMethod.POST)
@Api(tags = "FaceDetectApi", value = "FaceDetect Apis")
public class FaceDetectApi {

  @Autowired
  private FaceService faceService;

  /**
   * 人脸检测
   * 
   * @param param
   * @return
   * @throws FacePlatException
   * @throws IOException
   */
  @RequestMapping(value = "detect")
  @ApiOperation(value = "detect", notes = "人脸检测")
  @ApiImplicitParams({@ApiImplicitParam(name = "imageFile", paramType = "form", dataType = "file")})
  public RestResult<DetectResult> detect(@Valid @ModelAttribute DetectParam param)
      throws FacePlatException, IOException {
    byte[] img;
    if (param.getImageFile() != null) {
      img = param.getImageFile().getBytes();
    } else if (StringUtils.isNotEmpty(param.getImageUrl())) {
      img = ImageUtils.loadImage(param.getImageUrl());
    } else {
      throw new IllegalArgumentException("Neither image file nor url");
    }

    List<DetectedFace> detect = faceService.detect(img, param.isFaceAttributes());
    DetectResult result = new DetectResult();
    result.setFaces(detect);
    return new RestResult<>(result);
  }

  @RequestMapping(value = "face_attribute")
  @ApiOperation(value = "getFaceAttribute", notes = "人脸属性检测")
  @ApiImplicitParams({@ApiImplicitParam(name = "imageFile", paramType = "form", dataType = "file")})
  public RestResult<DetectResult> getFaceAttribute(@Valid @ModelAttribute DetectParam param)
      throws FacePlatException, IOException {
    byte[] img;
    if (param.getImageFile() != null) {
      img = param.getImageFile().getBytes();
    } else if (StringUtils.isNotEmpty(param.getImageUrl())) {
      img = ImageUtils.loadImage(param.getImageUrl());
    } else {
      throw new IllegalArgumentException("Neither image file nor url");
    }

    List<DetectedFace> detect = faceService.getFaceAttribute(img);
    DetectResult result = new DetectResult();
    result.setFaces(detect);
    return new RestResult<>(result);
  }

  /**
   * 人脸1:1
   * 
   * @param param
   * @return
   * @throws FacePlatException
   * @throws IOException
   */
  @RequestMapping(value = "compare")
  @ApiOperation(value = "compare", notes = "人脸比对")
  @ApiImplicitParams({@ApiImplicitParam(name = "imageFile1", paramType = "form", dataType = "file"),
      @ApiImplicitParam(name = "imageFile2", paramType = "form", dataType = "file")})
  @HystrixCommand
  public RestResult<CompareResult> compare(@Valid @ModelAttribute CompareParam param)
      throws FacePlatException, IOException {
    byte[] img1 = null;
    if (param.getImageFile1() != null) {
      img1 = param.getImageFile1().getBytes();
    } else if (StringUtils.isNotEmpty(param.getImageUrl1())) {
      img1 = ImageUtils.loadImage(param.getImageUrl1());
    }

    byte[] img2 = null;
    if (param.getImageFile2() != null) {
      img2 = param.getImageFile2().getBytes();
    } else if (StringUtils.isNotEmpty(param.getImageUrl2())) {
      img2 = ImageUtils.loadImage(param.getImageUrl2());
    }

    CompareResult result =
        faceService.compare(param.getFaceToken1(), param.getFaceToken2(), img1, img2);
    return new RestResult<>(result);
  }

  /**
   * 人脸1:N
   * 
   * @param param
   * @return
   * @throws FacePlatException
   * @throws IOException
   */
  @RequestMapping(value = "search")
  @ApiOperation(value = "search", notes = "人脸检索")
  @ApiImplicitParams({@ApiImplicitParam(name = "imageFile", paramType = "form", dataType = "file")})
  @HystrixCommand
  public RestResult<SearchResult> search(@Valid @ModelAttribute SearchParam param)
      throws FacePlatException, IOException {
    byte[] img = null;
    if (param.getImageFile() != null) {
      img = param.getImageFile().getBytes();
    } else if (StringUtils.isNotEmpty(param.getImageUrl())) {
      img = ImageUtils.loadImage(param.getImageUrl());
    }

    SearchResult result = faceService.search(param.getFaceToken(), img, param.getFacesetToken(),
        param.getResultCount());
    return new RestResult<>(result);
  }

}
