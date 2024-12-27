package com.mitocode.microservices.product_command_service.util;

import com.mitocode.microservices.common_models.model.dto.GenericModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaUtil {

    @Value("${kafka.mitocode.topicName:mitocode}")
    private String topicName;

    private final KafkaTemplate<String, GenericModel<?>> kafkaTemplate;

    public void sendMessage(GenericModel<?> message) {
        kafkaTemplate.send(topicName, message);
    }
}
