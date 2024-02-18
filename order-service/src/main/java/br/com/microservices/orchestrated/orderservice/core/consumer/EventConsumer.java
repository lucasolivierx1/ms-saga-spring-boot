package br.com.microservices.orchestrated.orderservice.core.consumer;

import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class EventConsumer {

    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.notify-ending}"
    )
    public void consumeNotifyEndingEvent(String message) {
        log.info("Receiving Ending notification event {} from notify-ending-topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }
}
