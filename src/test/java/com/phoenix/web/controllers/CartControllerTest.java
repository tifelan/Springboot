package com.phoenix.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql("/db/insert.sql")
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Add a new Item to cart")
    void addItemToCart() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CartRequestDto requestDto = new CartRequestDto();
        requestDto.setProductId(14L);
        requestDto.setQuantity(1);
        requestDto.setUserId(5011L);

        mockMvc.perform(post("/api/cart")
                .contentType("application/json")
                .content(mapper.writeValueAsString(requestDto)))
                .andExpect(status().is(200))
                .andDo(print());
    }
}