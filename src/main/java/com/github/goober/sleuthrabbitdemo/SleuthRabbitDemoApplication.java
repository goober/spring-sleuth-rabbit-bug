package com.github.goober.sleuthrabbitdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SleuthRabbitDemoApplication {

    Logger log = LoggerFactory.getLogger(SleuthRabbitDemoApplication.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SleuthRabbitDemoApplication.class, args);
    }

    @RabbitListener(queues = {RabbitConfiguration.queueName})
    public void handler() {
        log.info("Received message");
        throw new AmqpRejectAndDontRequeueException("The message should be rejected");
    }

    @Scheduled(fixedRate = 10000)
    public void publish() {
        log.info("Publish message");
        rabbitTemplate.convertAndSend( RabbitConfiguration.topicExchangeName, "", "test");
    }
}
