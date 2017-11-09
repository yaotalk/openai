package com.minivision.aop.kong.api.plugin.authentication;

import com.minivision.aop.kong.model.plugin.authentication.jwt.JwtCredential;
import com.minivision.aop.kong.model.plugin.authentication.jwt.JwtCredentialList;

/**
 * Created by vaibhav on 16/06/17.
 */
public interface JwtService {
    JwtCredential addCredentials(String consumerIdOrUsername, JwtCredential request);
    void deleteCredentials(String consumerIdOrUsername, String id);
    JwtCredentialList listCredentials(String consumerIdOrUsername);
}
