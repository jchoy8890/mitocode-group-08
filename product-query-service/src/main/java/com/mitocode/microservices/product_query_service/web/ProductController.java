package com.mitocode.microservices.product_query_service.web;


import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.product_query_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;


    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws InterruptedException {
        log.info("[ProductController] - [getAllProducts]");
//        TimeUnit.SECONDS.sleep(2L);
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/product/parameter")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithParam(@RequestParam("tokens") String token) throws InterruptedException {
        log.info("[ProductController] - [getAllProductsWithParam]");
//        TimeUnit.SECONDS.sleep(2L);
        log.info("Parametro: " + token);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product/slow/{flag}")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithFlagForSlow(@PathVariable("flag") boolean flag) throws Exception {

        if (flag) {
            log.info("Product Service slow");
            TimeUnit.MILLISECONDS.sleep(2400);
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/product/{flag}")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithFlag(@PathVariable("flag") boolean flag
//            , @RequestHeader("appCallerName") String appCallerName
    ) throws Exception {

        if (flag) {
            TimeUnit.MILLISECONDS.sleep(795);
//            throw new Exception("Probando Circuit Breaker");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithParameter(
            @RequestParam("flag") boolean flag,
            @RequestHeader("appCallerName") String appCallerName

    ) throws Exception {

        log.info("App Caller Name: " + appCallerName);

        if (flag) {
            throw new Exception("Probando Circuit Breaker");
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/api/mitocode/user")
    public ResponseEntity<String> testPrefix() {
        return ResponseEntity.ok("Response Ok");
    }

}
