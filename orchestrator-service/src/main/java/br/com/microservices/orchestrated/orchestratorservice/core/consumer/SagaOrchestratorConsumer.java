package br.com.microservices.orchestrated.orchestratorservice.core.consumer;

import br.com.microservices.orchestrated.orchestratorservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SagaOrchestratorConsumer {

    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.start-saga}"
    )
    public void consumeStartSagaEvent(String message) {
        log.info("Receiving event {} from notify-ending-topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }


    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.orchestrator}"
    )
    public void consumeOrchestratorEvent(String message) {
        log.info("Receiving event {} from orchestrator topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-success}"
    )
    public void consumeFinishSuccessEvent(String message) {
        log.info("Receiving event {} from finish-success topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.finish-fail}"
    )
    public void consumeFinishFailEvent(String message) {
        log.info("Receiving event {} from finish-fail topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }
}
