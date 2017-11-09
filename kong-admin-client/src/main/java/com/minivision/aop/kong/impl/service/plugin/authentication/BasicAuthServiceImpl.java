package com.minivision.aop.kong.impl.service.plugin.authentication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import com.minivision.aop.kong.api.plugin.authentication.BasicAuthService;
import com.minivision.aop.kong.exception.KongClientException;
import com.minivision.aop.kong.internal.plugin.authentication.RetrofitBasicAuthService;
import com.minivision.aop.kong.model.plugin.authentication.basic.BasicAuthCredential;

/**
 * Created by vaibhav on 15/06/17.
 */
public class BasicAuthServiceImpl implements BasicAuthService {

    private RetrofitBasicAuthService retrofitBasicAuthService;

    public BasicAuthServiceImpl(String adminUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adminUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitBasicAuthService = retrofit.create(RetrofitBasicAuthService.class);
    }

    @Override
    public void addCredentials(String consumerIdOrUsername, String username, String password) {
        try {
            retrofitBasicAuthService.addCredentials(consumerIdOrUsername, new BasicAuthCredential(username, password)).execute();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
    }
}
