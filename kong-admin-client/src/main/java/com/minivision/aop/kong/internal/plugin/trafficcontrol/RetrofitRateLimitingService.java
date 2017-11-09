package com.minivision.aop.kong.internal.plugin.trafficcontrol;

import com.minivision.aop.kong.model.plugin.trafficcontrol.ratelimiting.RateLimitingConfig;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitRateLimitingService {
	@POST("/apis/{api}/plugins")
    Call<Void> configRateLimitingByApi(@Path("api") String apiIdOrName, @Body RateLimitingConfig request);
	
	@POST("/plugins")
    Call<Void> configRateLimitingForAllApi(@Body RateLimitingConfig request);
}
