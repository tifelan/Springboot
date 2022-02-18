package com.phoenix.web.controllers;

import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import com.phoenix.service.cart.CartService;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartServiceImpl;

    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartRequestDto cartRequestDto){
        CartResponseDto cartResponseDto = null;
        try{
            cartResponseDto =
                    cartServiceImpl.addItemToCart(cartRequestDto);
        } catch (ProductDoesNotExistException | UserNotFoundException
                | BusinessLogicException e) {
           ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

       return ResponseEntity.ok().body(cartResponseDto);
    }
}
