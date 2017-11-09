package com.minivision.aop.kong.impl.service.plugin.authentication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import com.minivision.aop.kong.api.plugin.authentication.HmacAuthService;
import com.minivision.aop.kong.exception.KongClientException;
import com.minivision.aop.kong.internal.plugin.authentication.RetrofitHmacAuthService;
import com.minivision.aop.kong.model.plugin.authentication.hmac.HmacAuthCredential;

/**
 * Created by vaibhav on 15/06/17.
 */
public class HmacAuthServiceImpl implements HmacAuthService {

    private RetrofitHmacAuthService retrofitHmacAuthService;

    public HmacAuthServiceImpl(String adminUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adminUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitHmacAuthService = retrofit.create(RetrofitHmacAuthService.class);
    }

    @Override
    public void addCredentials(String consumerIdOrUsername, String username, String secret) {
        try {
            retrofitHmacAuthService.addCredentials(consumerIdOrUsername, new HmacAuthCredential(username, secret)).execute();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
    }
}
