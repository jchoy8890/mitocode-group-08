package com.mitocode.microservices.productservice.expose.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
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
    void when_call_get_product_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Master")));
    }

    @Test
    @Order(1)
    @DisplayName("Almacenar un nuevo producto")
    void when_call_save_product_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveProduct")
                        .content("{\n" +
                                "        \"productId\": \"P000010\",\n" +
                                "        \"productName\": \"Curso Microservicios Avanzado Master\",\n" +
                                "        \"productType\": \"Curso\",\n" +
                                "        \"price\": 105,\n" +
                                "        \"stock\": 5\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("Obtener todos los producto enviando un parámetro")
    void when_call_get_product_with_parameters_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/parameter")
                        .param("tokens", "cualquiercosa")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Micros")));
    }

    @Test
    @Order(4)
    @DisplayName("Obtener todos los producto omitiendo un parámetro")
    void when_call_get_product_with_parameters_then_return_400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/parameter"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(5)
//    @DisabledOnJre(JRE.JAVA_21)
//    @DisabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_16)
//    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_20})
//    @DisabledOnOs(OS.MAC)
//    @EnabledOnOs({OS.MAC, OS.WINDOWS})
//    @EnabledIfEnvironmentVariable(named = "LOGNAME", matches = "dev-kazum")
    @DisabledIfEnvironmentVariable(named = "LOGNAME", matches = "dev-kazum")
    @DisplayName("Obtener todos los producto enviando un path variable")
    void when_call_get_product_with_pathvariable_then_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{flag}", false))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Micros")));
    }


}