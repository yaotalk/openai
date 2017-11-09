package com.minivision.aop.portal.param;

import com.minivision.ai.rest.param.RestParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Setter
@Getter
@ToString
public class CreateAppParam extends RestParam {

  private static final long serialVersionUID = -2019333212509019983L;
  
//  @NotBlank(message = "用户名不能为空")
//  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  
  @NotBlank(message = "应用名称不能为空")
  @ApiModelProperty(value = "应用名称", required = true)
  private String appName;
  @ApiModelProperty(value = "应用描述")
  private String appDesc;
  @ApiModelProperty(value = "应用类型")
  private int appFunc;
  @NotBlank(message = "应用地址不能为空")
  @ApiModelProperty(value = "应用地址", required = true)
  private String appUrl;
  
  @NotBlank(message = "应用平台不能为空")
  @ApiModelProperty(value = "应用平台", required = true)
  private String appPlat;
  @NotBlank(message = "接入产品类型不能为空")
  @ApiModelProperty(value = "接入产品类型", required = true)
  private String callType;
  @NotBlank(message = "API类别不能为空")
  @ApiModelProperty(value = "API类别", required = true)
  private String apiGroup;

}
