package org.tpr.auth.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.registrationQueue}")
    private String registrationQueue;

    @Value("${rabbitmq.registrationQueueKey}")
    private String registrationKey;

    @Bean
    public Queue registrationQueue() {
        return new Queue(registrationQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding registrationQueueBinding() {
        return BindingBuilder.bind(registrationQueue()).to(exchange()).with(registrationKey);
    }
}
