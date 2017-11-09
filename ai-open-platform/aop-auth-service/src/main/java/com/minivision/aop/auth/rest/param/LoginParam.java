package com.minivision.aop.auth.rest.param;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginParam extends RestParam {

  private static final long serialVersionUID = -5087425200449046930L;

  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名", required = true)
  protected String username;
  
  @NotBlank(message = "密码不能为空")
  @ApiModelProperty(value = "密码", required = true)
  protected String password;
  
}
