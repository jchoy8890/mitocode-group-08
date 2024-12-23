package com.mitocode.microservices.common_models.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "audit")
public class AuditEntity {

    private Long currentTimestamp;
    private String appCallerName;
    private String opnNumber;
    private String message;
    private String statusCode;
    private String exception;
    private String exceptionDetails;

}