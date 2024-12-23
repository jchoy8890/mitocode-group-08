package com.mitocode.microservices.common_models.model.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {


    @Id
    private String productId;
    private String productName;
    private String productType;
    private Long price;
    private Integer stock;


}
