package ru.gavrilov.sergey.service;

/**
 * @author Danil_Slesarenko
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = "${app.topic.foo}")
    public void listen(@Payload String message) {
        log.info("received message='{}'", message);
        System.out.println(String.format("received message=%s", message));
    }
}
