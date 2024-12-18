package com.mitocode.microservices.productservice.service;


//import com.mitocode.common.stub.models.ErrorMitocode;
import com.mitocode.microservices.productservice.model.dto.ProductDTO;
import com.mitocode.microservices.productservice.model.entity.ProductEntity;
import com.mitocode.microservices.productservice.model.entity.ProductPostgresEntity;
import com.mitocode.microservices.productservice.service.repository.ProductPostgreSQLRepository;
import com.mitocode.microservices.productservice.service.repository.ProductRepository;
import com.mitocode.microservices.productservice.util.UtilMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {


//    Logger logger =  LoggerFactory.getLogger(ProductService.class);

    private final UtilMapper utilMapper;
    private final ProductRepository productRepository;
    private final ProductPostgreSQLRepository productPostgreSQLRepository;

    @Value("${server.port}")
    private Integer port;


    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = utilMapper.convertDTOtoEntity(productDTO);
        ProductPostgresEntity productPostgreSQLEntity = utilMapper.convertDTOtoEntityPostgreSQL(productDTO);
        productRepository.save(productEntity);
        productPostgreSQLRepository.save(productPostgreSQLEntity);
        productDTO.setPort(port);

        log.info("INFO");
        log.trace("TRACE");
        log.error("ERROR");
        log.warn("WARN");
        log.trace("TRACE");

        return productDTO;

    }


    public List<ProductDTO> getAllProducts() {

        Iterable<ProductEntity> itProducts = productRepository.findAll();

        return StreamSupport.stream(itProducts.spliterator(), false).map(productEntity -> {
            ProductDTO productDTO = ProductDTO.builder().build();
            BeanUtils.copyProperties(productEntity, productDTO);
            productDTO.setPort(port);

//            ErrorMitocode errorMitocode = new ErrorMitocode();
//            errorMitocode.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            errorMitocode.setMessage("Internal Server Error");
//
//            log.info(errorMitocode.toString());

            log.info("INFO");
            log.trace("TRACE");
            log.error("ERROR");
            log.warn("WARN");
            log.trace("TRACE");



            return productDTO;
        }).collect(Collectors.toList());


    }

    public String updateProduct(String productId, Integer quantity) {
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        productEntity.setStock(productEntity.getStock() - quantity);
        productRepository.save(productEntity);
        return "OK";
    }

}
