package com.mitocode.microservices.product_query_service_v2.util;

import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.common_models.model.entity.ProductPostgresEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilMapper {

    public ProductEntity convertDTOtoEntity(ProductDTO productDTO) {
        ProductEntity productEntity = ProductEntity.builder().build();
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
