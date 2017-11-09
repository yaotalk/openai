package com.minivision.aop.auth.rest.param;

import org.hibernate.validator.constraints.Email;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDeveloperParam extends LoginParam {

  private static final long serialVersionUID = 4867185900891706858L;

  @ApiModelProperty(value = "新密码")
  private String newpassword;
  
  @Email(message = "不是合法的邮箱")
  @ApiModelProperty(value = "邮箱")
  private String email;

}
