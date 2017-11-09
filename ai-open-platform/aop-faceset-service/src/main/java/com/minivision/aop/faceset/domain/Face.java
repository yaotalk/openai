package com.minivision.aop.faceset.domain;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Created by Administrator on 2017/3/15 0015.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Face {
    @Id
    @NotEmpty(message = "Id is required.")
    private String id;
    private String name;
    private String sex;
    private String idCard;

    private String phoneNumber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faceset_id", foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private FaceSet faceSet;
    private String imgpath;

    @Override
    public String toString() {
      return "Face [name=" + name + ", sex=" + sex + ", idCard=" + idCard + ", phoneNumber="
          + phoneNumber + "]";
    }

}
