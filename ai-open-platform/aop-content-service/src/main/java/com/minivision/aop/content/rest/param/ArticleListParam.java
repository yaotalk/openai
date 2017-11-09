package com.minivision.aop.content.rest.param;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ArticleListParam extends RestParam {

  private static final long serialVersionUID = -2851352798822213998L;
  
  @ApiModelProperty(value = "内容类型：1-消息，2-文章", required = true)
  @NotNull(message = "内容类型不能为null")
  @Range(min = 1, max = 2, message = "内容类型的值不正确")
  private Integer type;
  @ApiModelProperty(value = "页码，起始为0")
  private int page = 0;
  @ApiModelProperty(value = "每页条数，默认为20")
  private int limit = 20;

}
