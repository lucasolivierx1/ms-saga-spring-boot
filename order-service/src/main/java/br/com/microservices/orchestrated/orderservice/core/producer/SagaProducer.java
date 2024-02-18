package br.com.microservices.orchestrated.orderservice.core.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SagaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.start-saga}")
    private String startSagaTopic;

    public void sendEvent(String message) {
        try {
            log.info("Message sent to topic: {} with data {}", startSagaTopic, message);
            kafkaTemplate.send(startSagaTopic, message);
        } catch (Exception e) {
            log.error("Error sending message to topic: {} with data {}", startSagaTopic, message, e);
        }
    }
}