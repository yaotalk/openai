package com.minivision.aop.kong.api.plugin.authentication;

import com.minivision.aop.kong.model.plugin.authentication.key.KeyAuthCredential;

/**
 * Created by vaibhav on 15/06/17.
 */
public interface KeyAuthService {
	KeyAuthCredential addCredentials(String consumerIdOrUsername, String key);
}
