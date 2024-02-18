package br.com.microservices.orchestrated.paymentservice.core.consumer;

import br.com.microservices.orchestrated.paymentservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class PaymentConsumer {

    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-success}"
    )
    public void consumeSuccessEvent(String message) {
        log.info("Receiving event {} from payment-success topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }


    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.payment-fail}"
    )
    public void consumeFailEvent(String message) {
        log.info("Receiving rollback event {} from payment-fail topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }

}
