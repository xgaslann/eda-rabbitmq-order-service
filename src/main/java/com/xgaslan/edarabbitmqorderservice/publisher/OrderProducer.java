package com.xgaslan.edarabbitmqorderservice.publisher;

import com.xgaslan.edarabbitmqorderservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${rabbitmq.topic-exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing-key.name}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderEvent orderEvent) {
        log.info("Producing message: {}", orderEvent);
        rabbitTemplate.convertAndSend(exchange, routingKey, orderEvent);
        log.info("Message sent to exchange: {} with routing key: {}", exchange, routingKey);
    }
}
