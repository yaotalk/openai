package com.minivision.aop.auth.rest.param;

import org.hibernate.validator.constraints.NotBlank;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPwdParam extends RestParam {

  private static final long serialVersionUID = -1008272975865488261L;

  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名", required = true)
  private String username;
  
  @NotBlank(message = "新密码不能为空")
  @ApiModelProperty(value = "新密码", required = true)
  private String newpassword;
  
  @NotBlank(message = "验证码不能为空")
  @ApiModelProperty(value = "验证码", required = true)
  private String code;
  
}
