package com.minivision.aop.kong.internal.plugin.security;

import com.minivision.aop.kong.model.plugin.security.acl.Acl;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by vaibhav on 18/06/17.
 */
public interface RetrofitAclService {
    @POST("/consumers/{id}/acls")
    Call<Void> associateConsumer(@Path("id") String consumerIdOrUsername, @Body Acl request);
}
