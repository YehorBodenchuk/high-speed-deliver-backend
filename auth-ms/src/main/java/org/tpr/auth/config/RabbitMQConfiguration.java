package org.tpr.auth.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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

    @Value("${rabbitmq.parcelQueue}")
    private String parcelQueue;

    @Value("${rabbitmq.parcelQueueKey}")
    private String parcelKey;

    @Bean
    public Queue parcelQueue() {
        return new Queue(parcelQueue);
    }

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

    @Bean
    public Binding parcelQueueBinding() {
        return BindingBuilder.bind(parcelQueue()).to(exchange()).with(parcelKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
