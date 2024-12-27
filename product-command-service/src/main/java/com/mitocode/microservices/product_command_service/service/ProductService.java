package com.mitocode.microservices.product_command_service.service;


import com.mitocode.microservices.common_models.model.dto.GenericModel;
import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.common_models.model.entity.ProductPostgresEntity;
import com.mitocode.microservices.product_command_service.service.repository.ProductPostgreSQLRepository;
import com.mitocode.microservices.product_command_service.util.KafkaUtil;
import com.mitocode.microservices.product_command_service.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {


    private final UtilMapper utilMapper;
    private final ProductPostgreSQLRepository productPostgreSQLRepository;
    private final KafkaUtil kafkaUtil;

    @Value("${server.port}")
    private Integer port;


    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductPostgresEntity productPostgreSQLEntity = utilMapper.convertDTOtoEntityPostgreSQL(productDTO);
        productPostgreSQLRepository.save(productPostgreSQLEntity);
        kafkaUtil.sendMessage(new GenericModel<>(productPostgreSQLEntity, ProductPostgresEntity.class.getSimpleName()));
        productDTO.setPort(port);
        return productDTO;
    }


    public String updateProduct(String productId, Integer quantity) {
        ProductPostgresEntity productEntity = productPostgreSQLRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        productEntity.setStock(productEntity.getStock() - quantity);
        productPostgreSQLRepository.save(productEntity);
        return "OK";
    }

}
