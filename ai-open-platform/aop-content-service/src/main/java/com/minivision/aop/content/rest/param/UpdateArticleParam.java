package com.minivision.aop.content.rest.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateArticleParam extends RestParam {

  private static final long serialVersionUID = -8332049344962594628L;
  
  @NotNull(message = "ID不能为null")
  @Min(value = 1, message = "不是合法的ID")
  @ApiModelProperty(value = "主键ID", required = true)
  private Long id;

  @ApiModelProperty(value = "标题")
  private String title;
  @ApiModelProperty(value = "摘要")
  private String summary;
  @ApiModelProperty(value = "详细内容")
  private String content;
  @ApiModelProperty(value = "内容类型：1-消息，2-文章")
  private int type;
  @ApiModelProperty(value = "内容来源")
  private String source;
  @ApiModelProperty(value = "内容链接")
  private String url;
  
}
