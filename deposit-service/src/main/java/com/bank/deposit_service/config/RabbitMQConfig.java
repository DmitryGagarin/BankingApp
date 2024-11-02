package com.bank.deposit_service.config;

import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public final static String QUEUE_NAME = "USER_ID_QUEUE";
    public final static String HOST_NAME = "rabbitmq";
}
