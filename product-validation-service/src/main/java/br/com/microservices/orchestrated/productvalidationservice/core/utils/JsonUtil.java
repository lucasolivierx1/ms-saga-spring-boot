package br.com.microservices.orchestrated.productvalidationservice.core.utils;


import br.com.microservices.orchestrated.productvalidationservice.core.dto.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JsonUtil {

    private final ObjectMapper objectMapper;

    public Event toEvent(final String json) {
        try {
            return objectMapper.readValue(json, Event.class);
        } catch (Exception e) {
            log.error("Error to convert json to Event", e);
            return null;
        }
    }

    public String toJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error to convert object to json", e);
            return "";
        }
    }
}
