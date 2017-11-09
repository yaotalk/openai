package com.minivision.aop.kong.model.plugin.trafficcontrol.ratelimiting;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by vaibhav on 17/06/17.
 */
@Data
public class RateLimitingConfig {
	
	String name = "rate-limiting";
	@SerializedName("consumer_id")
	String consumerId;
    @SerializedName("config.second")
    Integer second;
    @SerializedName("config.minute")
    Integer minute;
    @SerializedName("config.hour")
    Integer hour;
    @SerializedName("config.day")
    Integer day;
    @SerializedName("config.month")
    Integer month;
    @SerializedName("config.year")
    Integer year;
    @SerializedName("config.limit_by")
    LimitBy limitBy;
    @SerializedName("config.policy")
    Policy policy;
    @SerializedName("config.fault_tolerant")
    Boolean faultTolerant;
    @SerializedName("config.redis_host")
    String redisHost;
    @SerializedName("config.redis_port")
    Integer redisPort;
    @SerializedName("config.redis_password")
    String redisPassword;
    @SerializedName("config.redis_timeout")
    Integer redisTimeout;
    @SerializedName("config.redis_database")
    Integer redisDatabase;

    public enum Policy {
        local, cluster, redis
    }

    public enum LimitBy {
        consumer, credential, ip
    }
}
