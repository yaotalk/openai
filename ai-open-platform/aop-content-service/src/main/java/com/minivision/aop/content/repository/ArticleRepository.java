package com.minivision.aop.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.minivision.aop.content.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Page<Article> findByTypeOrderByUpdateTimeDesc(Integer type, Pageable pageable);
  
}
