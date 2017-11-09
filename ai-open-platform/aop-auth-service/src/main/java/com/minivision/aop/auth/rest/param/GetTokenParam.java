package com.minivision.aop.auth.rest.param;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetTokenParam extends RestParam {

  private static final long serialVersionUID = 453216779073744761L;

  @NotBlank(message = "应用ID不能为空")
  @ApiModelProperty(value = "应用ID", required = true)
  private String appId;
  @NotBlank(message = "secretID不能为空")
  @ApiModelProperty(value = "secretID", required = true)
  private String secretID;
  @NotBlank(message = "secretKey不能为空")
  @ApiModelProperty(value = "secretKey", required = true)
  private String secretKey;
  
}
