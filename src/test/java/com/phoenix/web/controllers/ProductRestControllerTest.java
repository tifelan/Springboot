package com.phoenix.web.controllers;

import com.phoenix.data.models.Product;
import com.phoenix.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts={"/db/insert.sql"})
class ProductRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void updateProductTest() throws Exception {

        Product product = productRepository.findById(14L).orElse(null);
        assertThat(product).isNotNull();

        mockMvc.perform(patch("/api/product/14")
                .contentType("application/json-patch+json")
                .content(Files.readAllBytes(Path.of("src/main/resources/patch-with-valid-json-patch-payload.json"))))
                .andExpect(status().is(200))
                .andDo(print());

        product = productRepository.findById(14L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getDescription()).isEqualTo("This is a bamboo");

    }
}