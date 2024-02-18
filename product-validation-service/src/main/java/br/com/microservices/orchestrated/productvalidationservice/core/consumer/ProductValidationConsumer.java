package br.com.microservices.orchestrated.productvalidationservice.core.consumer;


import br.com.microservices.orchestrated.productvalidationservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ProductValidationConsumer {

    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-success}"
    )
    public void consumeSuccessEvent(String message) {
        log.info("Receiving event {} from product-validation-success topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }


    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.product-validation-fail}"
    )
    public void consumeFailEvent(String message) {
        log.info("Receiving rollback event {} from product-validation-fail topic", message);

        var event = jsonUtil.toEvent(message);

        log.info("Event received: {}", event.toString());
    }

}
