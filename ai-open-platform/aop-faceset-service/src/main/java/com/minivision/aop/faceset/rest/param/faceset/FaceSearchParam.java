package com.minivision.aop.faceset.rest.param.faceset;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.minivision.aop.faceset.rest.param.RestParam;

import javax.validation.constraints.Max;

public class FaceSearchParam extends RestParam {

  private static final long serialVersionUID = 181223335810349266L;

  @NotBlank(message = "file must not be null")
  @ApiModelProperty(value = "图片文件")
  private MultipartFile imgfile;

  @NotBlank(message = "fasetToken must not bu null")
  @ApiModelProperty(value = "fasetToken，逗号分隔", required = true)
  private String facesetTokens;

  @ApiModelProperty(value = "记录总数")
  @Max(100)
  private int limit = 10;

  private float threshold;

  public MultipartFile getImgfile() {
    return imgfile;
  }

  public void setImgfile(MultipartFile imgfile) {
    this.imgfile = imgfile;
  }

  public String getFacesetTokens() {
    return facesetTokens;
  }

  public void setFacesetTokens(String facesetTokens) {
    this.facesetTokens = facesetTokens;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public float getThreshold() {
    return threshold;
  }

  public void setThreshold(float threshold) {
    this.threshold = threshold;
  }

}
