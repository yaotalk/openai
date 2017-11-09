package com.minivision.aop.kong.api.plugin.trafficcontrol;

import com.minivision.aop.kong.model.plugin.trafficcontrol.ratelimiting.RateLimitingConfig;

public interface RateLimitingService {
	void configRateLimitingByApi(String apiIdOrName, RateLimitingConfig request);
	void configRateLimitingForAllApi(RateLimitingConfig request);
}
