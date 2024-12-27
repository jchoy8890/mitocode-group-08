package com.mitocode.microserice.audit_service.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitocode.microserice.audit_service.service.AuditRepository;
import com.mitocode.microserice.audit_service.service.ProductRepository;
import com.mitocode.microserice.audit_service.service.UserRepository;
import com.mitocode.microservices.common_models.model.dto.GenericModel;
import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.common_models.model.entity.AuditEntity;
import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.common_models.model.entity.ProductPostgresEntity;
import com.mitocode.microservices.common_models.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {


    private final ObjectMapper mapper;
    private final ProductRepository productRepository;
    private final AuditRepository auditRepository;
    private final UserRepository userRepository;

    @Value("${kafka.mitocode.server:localhost}")
    private String kafkaServer;

    @Value("${kafka.mitocode.port:9092}")
    private String kafkaPort;

    @Value("${kafka.mitocode.topicName:mitocode}")
    private String topicName;


    @Bean
    public ConsumerFactory<String, GenericModel<?>> consumerFactory() {
        Map<String, Object> kafkaProps = new HashMap<>();
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer + ":" + kafkaPort);
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, topicName);
//        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        kafkaProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        kafkaProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        kafkaProps.put(JsonDeserializer.KEY_DEFAULT_TYPE, "com.mitocode.microserice.audit_service.config.KafkaConfig");
        kafkaProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mitocode.microserice.audit_service.config.KafkaConfig");

        kafkaProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.mitocode.microservices.*");

        return new DefaultKafkaConsumerFactory<>(kafkaProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GenericModel<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GenericModel<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @KafkaListener(topics = "mitocode")
    public void listenTopic(GenericModel<?> message) {

        log.info("Message: " + message);


        if (message.getClassName().equals(ProductPostgresEntity.class.getSimpleName())) {

            ProductPostgresEntity productEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("Message: " + message);

            ProductEntity product = new ProductEntity();

            BeanUtils.copyProperties(productEntity, product);
            product.setTotal(product.getPrice() * product.getStock());

            productRepository.save(product);
        }
        if (message.getClassName().equals(UserEntity.class.getSimpleName())) {
            UserEntity userEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("Message: " + message);
            log.info("UserEntity: " + userEntity);
            userRepository.save(userEntity);
        }
        if (message.getClassName().equals(AuditEntity.class.getSimpleName())) {
            AuditEntity auditEntity = mapper.convertValue(message.getT(), new TypeReference<>() {
            });
            log.info("Message: " + message);
            log.info("UserEntity: " + auditEntity);
            auditRepository.save(auditEntity);
        }


    }

}
