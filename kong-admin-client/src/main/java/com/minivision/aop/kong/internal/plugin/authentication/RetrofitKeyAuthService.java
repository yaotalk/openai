package com.minivision.aop.kong.internal.plugin.authentication;

import com.minivision.aop.kong.model.plugin.authentication.key.KeyAuthCredential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by vaibhav on 15/06/17.
 */
public interface RetrofitKeyAuthService {

    @POST("/consumers/{id}/key-auth")
    Call<KeyAuthCredential> addCredentials(@Path("id") String consumerIdOrUsername, @Body KeyAuthCredential request);
}
