package com.github.goober.sleuthrabbitdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = {RabbitConfiguration.queueName})
    public void handler() {
        log.info("Received message");
        throw new AmqpRejectAndDontRequeueException("The message should be rejected");
    }
}
