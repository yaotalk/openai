package com.minivision.aop.kong.impl;

import com.minivision.aop.kong.api.admin.ApiService;
import com.minivision.aop.kong.api.admin.CertificateService;
import com.minivision.aop.kong.api.admin.ConsumerService;
import com.minivision.aop.kong.api.admin.PluginService;
import com.minivision.aop.kong.api.admin.SniService;
import com.minivision.aop.kong.api.admin.TargetService;
import com.minivision.aop.kong.api.admin.UpstreamService;
import com.minivision.aop.kong.api.plugin.authentication.BasicAuthService;
import com.minivision.aop.kong.api.plugin.authentication.HmacAuthService;
import com.minivision.aop.kong.api.plugin.authentication.JwtService;
import com.minivision.aop.kong.api.plugin.authentication.KeyAuthService;
import com.minivision.aop.kong.api.plugin.authentication.OAuth2Service;
import com.minivision.aop.kong.api.plugin.security.AclService;
import com.minivision.aop.kong.api.plugin.trafficcontrol.RateLimitingService;
import com.minivision.aop.kong.impl.helper.RetrofitServiceCreator;
import com.minivision.aop.kong.impl.service.plugin.authentication.BasicAuthServiceImpl;
import com.minivision.aop.kong.impl.service.plugin.authentication.HmacAuthServiceImpl;
import com.minivision.aop.kong.impl.service.plugin.authentication.KeyAuthServiceImpl;
import com.minivision.aop.kong.impl.service.plugin.security.AclServiceImpl;
import com.minivision.aop.kong.impl.service.plugin.trafficcontrol.RateLimitingServiceImpl;
import com.minivision.aop.kong.internal.admin.RetrofitApiService;
import com.minivision.aop.kong.internal.admin.RetrofitCertificateService;
import com.minivision.aop.kong.internal.admin.RetrofitConsumerService;
import com.minivision.aop.kong.internal.admin.RetrofitPluginService;
import com.minivision.aop.kong.internal.admin.RetrofitSniService;
import com.minivision.aop.kong.internal.admin.RetrofitTargetService;
import com.minivision.aop.kong.internal.admin.RetrofitUpstreamService;
import com.minivision.aop.kong.internal.plugin.authentication.RetrofitJwtService;
import com.minivision.aop.kong.internal.plugin.authentication.RetrofitOAuth2Service;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by vaibhav on 12/06/17.
 */
@Setter
@Getter
public class KongClient {

    private ConsumerService consumerService;
    private ApiService apiService;
    private PluginService pluginService;
    private CertificateService certificateService;
    private SniService sniService;
    private UpstreamService upstreamService;
    private TargetService targetService;

    private BasicAuthService basicAuthService;
    private KeyAuthService keyAuthService;
    private HmacAuthService hmacAuthService;
    private OAuth2Service oAuth2Service;
    private JwtService jwtService;
    
    private AclService aclService;
    
    private RateLimitingService rateLimitingService;

    public KongClient(String adminUrl) {
        RetrofitServiceCreator creator = new RetrofitServiceCreator(adminUrl);

        consumerService = creator.create(ConsumerService.class, RetrofitConsumerService.class);
        apiService = creator.create(ApiService.class, RetrofitApiService.class);
        pluginService = creator.create(PluginService.class, RetrofitPluginService.class);
        certificateService = creator.create(CertificateService.class, RetrofitCertificateService.class);
        sniService = creator.create(SniService.class, RetrofitSniService.class);
        upstreamService = creator.create(UpstreamService.class, RetrofitUpstreamService.class);
        targetService = creator.create(TargetService.class, RetrofitTargetService.class);

        basicAuthService = new BasicAuthServiceImpl(adminUrl);
        keyAuthService = new KeyAuthServiceImpl(adminUrl);
        hmacAuthService = new HmacAuthServiceImpl(adminUrl);
        oAuth2Service = creator.create(OAuth2Service.class, RetrofitOAuth2Service.class);
        jwtService = creator.create(JwtService.class, RetrofitJwtService.class);
        		
        aclService = new AclServiceImpl(adminUrl);
        
        rateLimitingService = new RateLimitingServiceImpl(adminUrl);
    }

}
