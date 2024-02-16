package br.com.microservices.orchestrated.inventoryservice.core.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.orchestrator}")
    private String orchestratorTopic;

    public void sendEvent(String message) {
        try {
            log.info("Message sent to topic: {} with data {}", orchestratorTopic, message);
            kafkaTemplate.send(orchestratorTopic, message);
        } catch (Exception e) {
            log.error("Error sending message to topic: {} with data {}", orchestratorTopic, message, e);
        }
    }
}
