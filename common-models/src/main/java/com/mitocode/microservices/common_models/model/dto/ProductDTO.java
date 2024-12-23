package com.mitocode.microservices.common_models.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String productId;
    private String productName;
    private Long price;
    private Integer stock;
    private String productType;
    private Integer port;


}

