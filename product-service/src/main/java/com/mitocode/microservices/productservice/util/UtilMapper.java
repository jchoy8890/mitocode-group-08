package com.mitocode.microservices.productservice.util;

import com.mitocode.microservices.productservice.model.dto.ProductDTO;
import com.mitocode.microservices.productservice.model.entity.ProductEntity;
import com.mitocode.microservices.productservice.model.entity.ProductPostgresEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilMapper {

    public ProductEntity convertDTOtoEntity(ProductDTO productDTO) {
        com.mitocode.microservices.productservice.model.entity.ProductEntity productEntity = com.mitocode.microservices.productservice.model.entity.ProductEntity.builder().build();
        BeanUtils.copyProperties(productDTO, productEntity);
        return productEntity;
    }

    public ProductPostgresEntity convertDTOtoEntityPostgreSQL(ProductDTO productDTO) {
        ProductPostgresEntity productEntity = ProductPostgresEntity.builder().build();
        BeanUtils.copyProperties(productDTO, productEntity);
        return productEntity;
    }


    public ProductDTO convertEntityToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = ProductDTO.builder().build();
        BeanUtils.copyProperties(productEntity, productDTO);
        return productDTO;
    }


}
