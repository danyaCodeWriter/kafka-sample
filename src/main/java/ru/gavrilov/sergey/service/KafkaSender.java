package ru.gavrilov.sergey.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Danil_Slesarenko
 */
@Slf4j
@Service
public class KafkaSender {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        @Value("${app.topic.foo}")
        private String topic;

        public void send(String message) {
            log.info("sending message='{}' to topic='{}'", message, topic);
            System.out.println(String.format("sending message=%s to topic=%s", message, topic));
            kafkaTemplate.send(topic, message);
        }
    }

