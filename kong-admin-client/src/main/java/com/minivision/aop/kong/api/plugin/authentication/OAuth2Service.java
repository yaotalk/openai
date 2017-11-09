package com.minivision.aop.kong.api.plugin.authentication;

import java.util.Map;

import com.minivision.aop.kong.model.plugin.authentication.oauth2.*;

/**
 * Created by vaibhav on 15/06/17.
 */
public interface OAuth2Service {
    Application createApplication(String consumerId, Application request);
    Token createToken(Token request);
    ApplicationList listApplications(String clientId);
    Map<String, Object> authorize(Authorization request);
    Token refreshToken(Refresh request);
}
