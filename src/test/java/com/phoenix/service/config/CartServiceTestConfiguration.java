package com.phoenix.service.config;

import com.phoenix.data.repository.CartRepository;
import com.phoenix.service.cart.CartService;
import com.phoenix.service.cart.CartServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class CartServiceTestConfiguration {

    @Bean
    @Profile("mock")
    public CartService mockCartService() {
        return Mockito.mock(CartService.class);
    }

    @Bean
    @Profile("mock")
    public CartRepository mockCartRepository() {
        return Mockito.mock(CartRepository.class);
    }

    @Bean
    @Profile("test")
    public CartService cartService() {
        return new CartServiceImpl();
    }




}
