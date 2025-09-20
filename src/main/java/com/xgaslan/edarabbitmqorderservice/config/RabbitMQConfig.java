package com.xgaslan.edarabbitmqorderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.order.name}")
    private String orderQueueName;

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueueName;

    @Value("${rabbitmq.topic-exchange.name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing-key.order.name}")
    private String orderRoutingKeyName;

    @Value("${rabbitmq.routing-key.email.name}")
    private String emailRoutingKeyName;

    @Bean
    public Queue orderQueue() {
        return new Queue(orderQueueName);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueueName);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(topicExchange())
                .with(orderRoutingKeyName);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(topicExchange())
                .with(emailRoutingKeyName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
