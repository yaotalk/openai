package com.minivision.aop.kong.impl.service.plugin.trafficcontrol;

import java.io.IOException;

import com.minivision.aop.kong.api.plugin.trafficcontrol.RateLimitingService;
import com.minivision.aop.kong.exception.KongClientException;
import com.minivision.aop.kong.internal.plugin.trafficcontrol.RetrofitRateLimitingService;
import com.minivision.aop.kong.model.plugin.trafficcontrol.ratelimiting.RateLimitingConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RateLimitingServiceImpl implements RateLimitingService {
	
	private RetrofitRateLimitingService retrofitRateLimitingService;
	
	public RateLimitingServiceImpl(String adminUrl) {
		Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(adminUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
		retrofitRateLimitingService = retrofit.create(RetrofitRateLimitingService.class);
	}

	@Override
	public void configRateLimitingByApi(String apiIdOrName, RateLimitingConfig request) {
		try {
			retrofitRateLimitingService.configRateLimitingByApi(apiIdOrName, request).execute();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
	}

	@Override
	public void configRateLimitingForAllApi(RateLimitingConfig request) {
		try {
			retrofitRateLimitingService.configRateLimitingForAllApi(request).execute();
        } catch (IOException e) {
            throw new KongClientException(e.getMessage());
        }
	}

}
