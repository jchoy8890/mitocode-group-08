package com.mitocode.microservices.product_command_service.config;

import com.mitocode.microservices.common_models.model.dto.GenericModel;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.mitocode.server:localhost}")
    private String kafkaServer;

    @Value("${kafka.mitocode.port:9092}")
    private String kafkaPort;


    public ProducerFactory<String, GenericModel<?>> producerFactory() {

        Map<String, Object> kafkaProps = new HashMap<>();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer + ":" + kafkaPort);
//        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);


        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(kafkaProps);
    }


    @Bean
    public KafkaTemplate<String, GenericModel<?>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


}
