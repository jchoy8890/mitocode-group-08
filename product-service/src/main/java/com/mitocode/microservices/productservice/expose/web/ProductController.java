package com.mitocode.microservices.productservice.expose.web;


import com.mitocode.microservices.common_models.model.dto.ProductDTO;
import com.mitocode.microservices.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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
//@OpenAPIDefinition(
//        info = @Info(
//                version = "0.0.1",
//                description = "Módulo del ecosistemas de Arquitectura de Microservicios para gestionar productos",
//                title = "Product Service Microservice",
//                contact = @Contact(
//                        name = "Mitocode Network",
//                        email = "mitocode@mitocodenetwork.com",
//                        url = "https://github.com/jchoy8890"
//                ),
//                license = @License(
//                        url = "https://github.com/jchoy8890",
//                        name = "Some license"
//                )
//        ),
//        servers = {
//                @Server(url = "http://localhost:9020", description = "Instancia 1"),
//                @Server(url = "http://localhost:9021", description = "Instancia2")
//        },
//        tags = {
//                @Tag(name = "ProductQuery", description = "Grupo de endpoints que filtra productos"),
//                @Tag(name = "ProductCommand", description = "Grupo de endpoints que modifican productos")
//        }
//)
public class ProductController {


    private final ProductService productService;

    //    @Operation(
//            description = "Método para obtener los productos",
//            tags = {"ProductQuery"},
//            responses = {
//                    @ApiResponse(description = "Response OK", responseCode = "200",
//                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
//                            )
//                    ),
//                    @ApiResponse(description = "Bad Request", responseCode = "400",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(
//                                            implementation = ErrorResponse.class
//                                    )
//                            )
//                    )
//            }
//    )
    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts() throws InterruptedException {
        log.info("[ProductController] - [getAllProducts]");
//        TimeUnit.SECONDS.sleep(2L);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //    @Operation(
//            description = "Método para guardar productos",
//            tags = {"ProductCommand"}
//    )
    @PostMapping("/saveProduct")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        log.info("[ProductController] - [saveProduct]: " + productDTO);
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @Operation(
            description = "Método para filtrar productos",
            tags = {"ProductQuery"}
    )
    @GetMapping("/product/parameter")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithParam(@RequestParam("tokens") String token) throws InterruptedException {
        log.info("[ProductController] - [getAllProductsWithParam]");
//        TimeUnit.SECONDS.sleep(2L);
        log.info("Parametro: " + token);
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            description = "Método para filtrar productos",
            tags = {"ProductQuery"}
    )
    @GetMapping("/product/slow/{flag}")
    public ResponseEntity<List<ProductDTO>> getAllProductsWithFlagForSlow(@PathVariable("flag") boolean flag) throws Exception {

        if (flag) {
            log.info("Product Service slow");
            TimeUnit.MILLISECONDS.sleep(2400);
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @Operation(
            description = "Método para filtrar productos",
            tags = {"ProductQuery"}
    )
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

    @Operation(
            description = "Método para filtrar productos",
            tags = {"ProductQuery"}
    )
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

    @Operation(
            description = "Método para actualizar productos",
            tags = {"ProductCommand"}
    )
    @PutMapping("/product/reserve/{productId}/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> reserve(@PathVariable("productId") String productId,
                                          @PathVariable("quantity") Integer quantity) {
        return ResponseEntity.ok(productService.updateProduct(productId, quantity));
    }

    @Operation(
            description = "Método para filtrar productos",
            tags = {"ProductQuery"}
    )
    @GetMapping("/api/mitocode/user")
    public ResponseEntity<String> testPrefix() {
        return ResponseEntity.ok("Response Ok");
    }

}
