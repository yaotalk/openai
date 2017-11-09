package com.minivision.aop.content.rest.param;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.minivision.ai.rest.param.RestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateArticleParam extends RestParam {

  private static final long serialVersionUID = -229476429589840447L;
  
  @NotBlank(message = "标题不能为空")
  @ApiModelProperty(value = "标题", required = true)
  private String title;
  @NotBlank(message = "摘要不能为空")
  @ApiModelProperty(value = "摘要", required = true)
  private String summary;
  @ApiModelProperty(value = "详细内容")
  private String content;
  @Range(min = 1, max = 2, message = "请选择内容类型")
  @ApiModelProperty(value = "内容类型：1-消息，2-文章", required = true)
  private int type;
  @ApiModelProperty(value = "内容来源")
  private String source;
  @ApiModelProperty(value = "内容链接")
  private String url;

}
