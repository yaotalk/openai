package com.minivision.aop.kong.model.plugin.authentication.jwt;

import java.util.List;

import com.minivision.aop.kong.model.common.AbstractEntityList;

/**
 * Created by vaibhav on 16/06/17.
 */
public class JwtCredentialList extends AbstractEntityList {
    Long total;
    List<JwtCredential> data;
}
