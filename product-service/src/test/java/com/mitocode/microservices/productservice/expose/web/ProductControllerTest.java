package com.mitocode.microservices.productservice.expose.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(2)
    @DisplayName("Obtener todos los productos")
//    @EnabledIfEnvironmentVariable(named = "environment", matches = "prod")
    @DisabledIfEnvironmentVariable(named = "USER", matches = "dev-kazum")
    void when_call_get_all_products_then_return_200() throws Exception {

//        System.getenv().forEach((k, v) -> {
//            System.out.println(k + " : " + v);
//        });


        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Master")));
    }

    @Test
    @Order(1)
    @DisplayName("Almacenar un nuevo producto")
//    @DisabledIfEnvironmentVariable(named = "environment", matches = "prod")
//    @EnabledIfEnvironmentVariable(named = "environment", matches = "prod")
    @DisabledIfEnvironmentVariable(named = "USER", matches = "dev-kazum")
    void when_call_post_save_product_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveProduct")
                        .content("{\n" +
                                "        \"productId\": \"P000011\",\n" +
                                "        \"productName\": \"Curso Microservicios Avanzado Master\",\n" +
                                "        \"productType\": \"Curso\",\n" +
                                "        \"price\": 105,\n" +
                                "        \"stock\": 5\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Microservicios")));
    }


    @Test
    @Order(3)
    @DisplayName("Obtener todos los producto enviando un parámetro")
    void when_call_get_product_with_parameters_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/parameter")
                        .param("tokens", "mitocode"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("Obtener todos los producto enviando un parámetro")
    void when_call_get_product_with_parameters_then_return_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/parameter")
                        .param("token", "mitocode"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("Obtener todos los producto enviando un path variable")
    void when_call_get_product_with_path_variable_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{flag}", false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(5)
//    @EnabledOnJre({JRE.JAVA_21, JRE.JAVA_17})
//    @DisabledOnJre(JRE.JAVA_21)
//    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_22)
//    @DisabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_16)
//    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    @DisabledOnOs({OS.MAC, OS.WINDOWS})
    @DisplayName("Obtener todos los producto enviando un parámetro_and_header")
    void when_call_get_product_with_parameter_and_header_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .param("flag", "false")
                        .header("appCallerName", "Mitocode"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Order(8)
    @DisplayName("Obtener todos los producto enviando un path variable")
    void when_call_get_product_with_path_variable_then_return_500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{flag}", true))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}