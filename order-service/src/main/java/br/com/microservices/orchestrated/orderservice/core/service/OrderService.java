package br.com.microservices.orchestrated.orderservice.core.service;

import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.document.Order;
import br.com.microservices.orchestrated.orderservice.core.dto.OrderRequest;
import br.com.microservices.orchestrated.orderservice.core.producer.SagaProducer;
import br.com.microservices.orchestrated.orderservice.core.repository.OrderRepository;
import br.com.microservices.orchestrated.orderservice.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    private static final String TRANSACTION_ID_PATTERN = "%s_%s";

    private final EventService eventService;
    private final SagaProducer sagaProducer;
    private final JsonUtil jsonUtil;
    private final OrderRepository orderRepository;

    public Order createOrder(OrderRequest orderRequest) {
        var order = Order
                .builder()
                .products(orderRequest.products())
                .createdAt(LocalDateTime.now())
                .transactionId(
                        String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID())
                )
                .build();
        log.info("Creating order");

        order = orderRepository.save(order);

        sagaProducer.sendEvent(jsonUtil.toJson(createPayload(order)));

        return order;
    }

    private Event createPayload(Order order) {
        log.info("Creating event");
        var event = Event
                .builder()
                .orderId(order.getId())
                .transactionId(order.getTransactionId())
                .createdAt(LocalDateTime.now())
                .payload(order)
                .source("order-service")
                .status("CREATED")
                .build();

        return eventService.saveEvent(event);
    }
}
