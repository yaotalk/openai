package com.minivision.aop.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.minivision.ai.util.ChunkRequest;
import com.minivision.aop.content.domain.Article;
import com.minivision.aop.content.repository.ArticleRepository;
import com.minivision.aop.content.rest.param.ArticleListParam;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = {Exception.class})
@Slf4j
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleRepository articleRepo;

  @Override
  public Article createArticle(Article article) {
    return articleRepo.saveAndFlush(article);
  }

  @Override
  public Article updateArticle(Article article) {
    Article exists = articleRepo.findOne(article.getId());
    if (StringUtils.hasText(article.getTitle())) {
      exists.setTitle(article.getTitle());
    }
    if (StringUtils.hasText(article.getSummary())) {
      exists.setSummary(article.getSummary());
    }
    if (StringUtils.hasText(article.getContent())) {
      exists.setContent(article.getContent());
    }
    if (article.getType() != 0) {
      exists.setType(article.getType());
    }
    if (StringUtils.hasText(article.getSource())) {
      exists.setSource(article.getSource());
    }
    if (StringUtils.hasText(article.getUrl())) {
      exists.setUrl(article.getUrl());
    }
    return articleRepo.saveAndFlush(exists);
  }

  @Override
  public void deleteArticle(Long id) {
    articleRepo.delete(id);
  }

  @Override
  public Article findArticle(Long id) {
    return articleRepo.findOne(id);
  }

  @Override
  public Page<Article> findArticles(ArticleListParam param) {
    int offset = param.getPage() * param.getLimit();
    return articleRepo.findByTypeOrderByUpdateTimeDesc(param.getType(), new ChunkRequest(offset, param.getLimit()));
  }
  
}
