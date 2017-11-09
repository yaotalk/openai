package com.minivision.aop.kong.impl.service.plugin.authentication;

import java.io.IOException;

import com.minivision.aop.kong.api.plugin.authentication.KeyAuthService;
import com.minivision.aop.kong.exception.KongClientException;
import com.minivision.aop.kong.internal.plugin.authentication.RetrofitKeyAuthService;
import com.minivision.aop.kong.model.plugin.authentication.key.KeyAuthCredential;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vaibhav on 15/06/17.
 */
public class KeyAuthServiceImpl implements KeyAuthService {

    private RetrofitKeyAuthService retrofitKeyAuthService;

    public KeyAuthServiceImpl(String adminUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adminUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitKeyAuthService = retrofit.create(RetrofitKeyAuthService.class);
    }

    @Override
    public KeyAuthCredential addCredentials(String consumerIdOrUsername, String key) {
        try {
        	Response<KeyAuthCredential> response = retrofitKeyAuthService.addCredentials(consumerIdOrUsername, new KeyAuthCredential(key)).execute();
        	return response.body();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
    }
}
