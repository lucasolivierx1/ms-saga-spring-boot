package br.com.microservices.orchestrated.orderservice.core.service;

import br.com.microservices.orchestrated.orderservice.config.exception.ValidationException;
import br.com.microservices.orchestrated.orderservice.core.document.Event;
import br.com.microservices.orchestrated.orderservice.core.dto.EventFilters;
import br.com.microservices.orchestrated.orderservice.core.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;


    public void notifyEnding(final Event event) {
        event.setOrderId(event.getOrderId());
        event.setCreatedAt(LocalDateTime.now());
        saveEvent(event);
        log.info("Order {} with saga notified! TransactionId : {}", event.getOrderId(), event.getTransactionId());
    }

    public Event saveEvent(final Event event) {
        log.info("Saving event: {}", event);
        return eventRepository.save(event);
    }

    public List<Event> getEvents() {
        log.info("Getting all events");
        return eventRepository.findAllByOrderByCreatedAtDesc();
    }

    public Event findByFilters(EventFilters filters) {
        validateEmptyFilters(filters);

        if (!isEmpty(filters.orderId())) {
            return findByOrderId(filters.orderId());
        } else {
            return findByTransactionId(filters.transactionId());
        }
    }

    private Event findByOrderId(String orderId) {
        return eventRepository.findTop1ByOrderIdOrderByCreatedAtDesc(orderId)
                .orElseThrow(() -> new ValidationException("Event not found for orderId: " + orderId));
    }

    private Event findByTransactionId(String transactionId) {
        return eventRepository.findTop1ByTransactionIdOrderByCreatedAtDesc(transactionId)
                .orElseThrow(() -> new ValidationException("Event not found for transactionId: " + transactionId));
    }

    private void validateEmptyFilters(EventFilters filters) {
        if (isEmpty(filters.orderId()) && isEmpty(filters.transactionId())) {
            throw new ValidationException("OrderId or TransactionId must be informed");
        }
    }

}
