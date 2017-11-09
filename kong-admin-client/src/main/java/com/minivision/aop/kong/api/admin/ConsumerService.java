package com.minivision.aop.kong.api.admin;

import com.minivision.aop.kong.model.admin.consumer.Consumer;
import com.minivision.aop.kong.model.admin.consumer.ConsumerList;

/**
 * Created by vaibhav on 13/06/17.
 */
public interface ConsumerService {
    Consumer createConsumer(Consumer request);
    Consumer getConsumer(String usernameOrId);
    Consumer updateConsumer(String usernameOrId, Consumer request);
    Consumer createOrUpdateConsumer(Consumer request);
    Consumer deleteConsumer(String usernameOrId);
    ConsumerList listConsumers(String id, String customId, String username, Long size, String offset);
}
