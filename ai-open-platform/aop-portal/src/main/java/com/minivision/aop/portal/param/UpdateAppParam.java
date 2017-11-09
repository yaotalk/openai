package com.minivision.aop.portal.param;

import com.minivision.ai.rest.param.RestParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class UpdateAppParam extends RestParam {

  private static final long serialVersionUID = 1830905666881779666L;
  
//  @NotBlank(message = "用户名不能为空")
//  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  
  @NotNull(message = "ID不能为null")
  @Min(value = 1, message = "不是合法的ID")
  @ApiModelProperty(value = "主键ID", required = true)
  private Long id;

  @ApiModelProperty(value = "应用描述")
  private String appDesc;
  @ApiModelProperty(value = "应用类型")
  private int appFunc;
  
  @ApiModelProperty(value = "应用平台")
  private String appPlat;
  @ApiModelProperty(value = "接入产品类型")
  private String callType;
  @ApiModelProperty(value = "API类别")
  private String apiGroup;
  
}
