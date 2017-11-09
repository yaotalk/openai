package com.minivision.aop.content.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minivision.ai.domain.IdEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Article extends IdEntity {

  private static final long serialVersionUID = -2094286331876972715L;
  
  @Column(nullable = false)
  private String title;
  @Column(length = 2000, nullable = false)
  private String summary;
  private String content;
  
  private int type;
  
  @Column(length = 100)
  private String source;
  @Column(length = 500)
  private String url;
  
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(nullable = false)
  private Date createTime;
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  @Column(nullable = false)
  private Date updateTime;

}
