package com.minivision.aop.content.rest.result;

import java.util.List;

import com.minivision.aop.content.domain.Article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@ApiModel
public class ArticleListResult {
  
  @ApiModelProperty(value = "每页内容列表")
  private List<Article> articleList;
  @ApiModelProperty(value = "总页数")
  private int pages;
  @ApiModelProperty(value = "总条数")
  private long total;

}
