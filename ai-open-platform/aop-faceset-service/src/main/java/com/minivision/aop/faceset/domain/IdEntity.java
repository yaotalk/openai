package com.minivision.aop.faceset.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class IdEntity implements Serializable{

  private static final long serialVersionUID = 4032439140381884952L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  protected Long id;

}
