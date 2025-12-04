package com.brian.userauthenticationservice.utils;

import com.brian.userauthenticationservice.event.Events;
import com.brian.userauthenticationservice.event.UserEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaUtils {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaUtils(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishEvent(Events event) {
        int partition = event.getPartition();
        publish(event, partition);
    }

    private void publish(Events event, int partition) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            String topic = event.getTopicName();
            String key = event.getKey();
            // Use ProducerRecord to set partition explicitly
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, key, payload);
            kafkaTemplate.send(record)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Sent to partition {}: {}", partition, payload);
                        } else {
                            log.error("Failed to send: {}", ex.getMessage());
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
