package com.mitocode.microservices.product_query_service_v2.service;


//import com.mitocode.common.stub.models.ErrorMitocode;

import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.common_models.model.entity.ProductEntity;
import com.mitocode.microservices.product_query_service_v2.service.repository.ProductRepository;
import com.mitocode.microservices.product_query_service_v2.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {


    private final UtilMapper utilMapper;
    private final ProductRepository productRepository;

    @Value("${server.port}")
    private Integer port;


    public List<ProductDTO> getAllProducts() {

        Iterable<ProductEntity> itProducts = productRepository.findAll();

        return StreamSupport.stream(itProducts.spliterator(), false).map(productEntity -> {
            ProductDTO productDTO = ProductDTO.builder().build();
            BeanUtils.copyProperties(productEntity, productDTO);
            productDTO.setPort(port);


            return productDTO;
        }).collect(Collectors.toList());


    }


}
