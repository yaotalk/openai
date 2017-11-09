package com.minivision.aop.content.service;

import org.springframework.data.domain.Page;

import com.minivision.aop.content.domain.Article;
import com.minivision.aop.content.rest.param.ArticleListParam;

public interface ArticleService {

  Article createArticle(Article article);
  Article updateArticle(Article article);
  void deleteArticle(Long id);
  Article findArticle(Long id);
  Page<Article> findArticles(ArticleListParam param);
  
}
