package com.minivision.aop.kong.model.admin.certificate;

import lombok.Data;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 13/06/17.
 */
@Data
public class CertificateList extends AbstractEntityList {
    Long total;
    List<Certificate> data;
}
