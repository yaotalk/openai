package com.minivision.aop.faceset.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FaceSet {

  @Id
  @NotEmpty(message = "token is required.")
  private String token;

  @Column(name = "faceset_name")
  @NotEmpty(message = "name is required.")
  private String name;

  @Column(name = "faceset_status", length = 4)
  private int status;

  @Transient
  private int faceCount;

  @Temporal(TemporalType.DATE)
  private Date createTime;

  private int capacity;

  public FaceSet(String facesetToken) {
    this.token = facesetToken;
  }

  @JsonIgnore
  @org.hibernate.annotations.ForeignKey(name = "none")
  @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy="faceSet")
  private Set<Face> faces;

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((token == null) ? 0 : token.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FaceSet other = (FaceSet) obj;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

}
