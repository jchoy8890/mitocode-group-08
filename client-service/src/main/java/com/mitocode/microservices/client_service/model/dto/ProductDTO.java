package com.mitocode.microservices.client_service.model.dto;

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
