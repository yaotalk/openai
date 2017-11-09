package com.minivision.aop.auth.domain;

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
public class ApiInfo extends IdEntity {

  private static final long serialVersionUID = -6378912909582709191L;

  private int apiGroup;
  @Column(length = 50, nullable = false, unique = true)
  private String apiId;
  @Column(length = 500, nullable = false)
  private String apiUrl;
  @Column(length = 5000)
  private String apiDesc;
  
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
