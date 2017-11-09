package com.minivision.aop.auth.rest.param;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateDeveloperParam extends LoginParam {

  private static final long serialVersionUID = -4343772266578997493L;

  @Email(message = "不是合法的邮箱")
  @NotBlank(message = "邮箱不能为空")
  @ApiModelProperty(value = "邮箱", required = true)
  private String email;

}
