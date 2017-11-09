package com.minivision.aop.kong.impl.service.plugin.security;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import com.minivision.aop.kong.api.plugin.security.AclService;
import com.minivision.aop.kong.exception.KongClientException;
import com.minivision.aop.kong.internal.plugin.security.RetrofitAclService;
import com.minivision.aop.kong.model.plugin.security.acl.Acl;

/**
 * Created by vaibhav on 18/06/17.
 */
public class AclServiceImpl implements AclService {

    private RetrofitAclService retrofitAclService;

    public AclServiceImpl(String adminUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adminUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAclService = retrofit.create(RetrofitAclService.class);
    }
    @Override
    public void associateConsumer(String usernameOrId, String group) {
        try {
            retrofitAclService.associateConsumer(usernameOrId, new Acl(group)).execute();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
    }
}
