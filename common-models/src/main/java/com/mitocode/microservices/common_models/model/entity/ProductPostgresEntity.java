package com.mitocode.microservices.common_models.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_product")
public class ProductPostgresEntity {

    @Id
    private String productId;
    private String productName;
    private String productType;
    private Long price;
    private Integer stock;


}
