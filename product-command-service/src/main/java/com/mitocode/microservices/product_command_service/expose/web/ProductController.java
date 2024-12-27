package com.mitocode.microservices.product_command_service.expose.web;


import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.product_command_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/saveProduct")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        log.info("[ProductController] - [saveProduct]: " + productDTO);
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }


    @PutMapping("/product/reserve/{productId}/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> reserve(@PathVariable("productId") String productId,
                                          @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.ok(productService.updateProduct(productId, quantity));
    }

}
