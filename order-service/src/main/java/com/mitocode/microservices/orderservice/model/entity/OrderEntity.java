package com.mitocode.microservices.orderservice.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "order")
public class OrderEntity {

    @Id
    private String orderId;
    private Integer status; // 0 Created, 1 Completed, 2 Updated with products, 3 Canceled
    private Long creationDate;
    private Long updateDate;
    private String productId;
    private Integer quantity;

}
