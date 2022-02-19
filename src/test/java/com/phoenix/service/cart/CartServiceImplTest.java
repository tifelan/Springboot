package com.phoenix.service.cart;

import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.dto.CartResponseDto;
import com.phoenix.data.dto.CartUpdateDto;
import com.phoenix.data.dto.QuantityOperation;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Cart;
import com.phoenix.data.models.Item;
import com.phoenix.data.models.Product;
import com.phoenix.data.repository.AppUserRepository;
import com.phoenix.data.repository.CartRepository;
import com.phoenix.data.repository.ProductRepository;
import com.phoenix.service.config.CartServiceTestConfiguration;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
@Sql("/db/insert.sql")
@Slf4j
class CartServiceImplTest {

    @Autowired
    CartService cartService;


    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;

    CartUpdateDto updateDto;

    @BeforeEach
    void setUp() {

        updateDto = CartUpdateDto.builder()
                        .itemId(510L)
                        .quantityOp(QuantityOperation.INCREASE)
                        .userId(5005L).build();
    }

//    @Test
//    @DisplayName("Add item to cart test")
//    void addItemToCart() throws UserNotFoundException, BusinessLogicException, ProductDoesNotExistException {
//
//        CartRequestDto cartRequestDto = new CartRequestDto();
//        cartRequestDto.setProductId(13L);
//        cartRequestDto.setUserId(5011L);
//        cartRequestDto.setQuantity(1);
//
//        assertThat(cartRequestDto.getQuantity()).isEqualTo(1);
//
//        when(cartServiceMock.addItemToCart(cartRequestDto)).thenReturn(CartResponseDto);
//
//        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
//        log.info("CartResponse --> {}", cartResponseDto);
//
//        assertThat(cartResponseDto.getCartItems()).isNotNull();
//        assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);
//
//        Item item = cartResponseDto.getCartItems().get(0);
//        log.info("Item --> {}", item);
//        assertThat(item.getQuantityAddedToCart()).isEqualTo(1);
//    }

    @Test
    @DisplayName("Add item to cart test")
    void addItemToCart() throws UserNotFoundException, BusinessLogicException, ProductDoesNotExistException {

       CartRequestDto cartRequestDto = new CartRequestDto();
       cartRequestDto.setProductId(13L);
       cartRequestDto.setUserId(5011L);
       cartRequestDto.setQuantity(1);

       assertThat(cartRequestDto.getQuantity()).isEqualTo(1);

        CartResponseDto cartResponseDto = null;

       cartResponseDto = cartService.addItemToCart(cartRequestDto);

       assertThat(cartResponseDto.getCartItems()).isNotNull();
       assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);

       Item item = cartResponseDto.getCartItems().get(0);
       log.info("Item --> {}", item);
       assertThat(item.getQuantityAddedToCart()).isEqualTo(1);
    }

    @Test
    @DisplayName("Cart price updated test")
    void updateCartPriceTest() throws UserNotFoundException, BusinessLogicException, ProductDoesNotExistException {
        CartRequestDto cartRequestDto = new CartRequestDto();
        cartRequestDto.setProductId(13L);
        cartRequestDto.setUserId(5011L);
        cartRequestDto.setQuantity(2);

        CartResponseDto cartResponseDto = cartService.addItemToCart(cartRequestDto);
        assertThat(cartResponseDto.getCartItems()).isNotNull();
        assertThat(cartResponseDto.getCartItems().size()).isEqualTo(1);
        assertThat(cartResponseDto.getTotalPrice()).isEqualTo(1000);

    }

    @Test
    @DisplayName("Increase cart item quantity test")
    void updateCartItemTest() throws UserNotFoundException, BusinessLogicException {

        AppUser appUser = appUserRepository.
                findById(updateDto.getUserId()).orElse(null);
        assertThat(appUser).isNotNull();
        Cart userCart = appUser.getMyCart();
        assertThat(userCart.getItemList().size()).isEqualTo(1);
        Item item = userCart.getItemList().get(0);
        log.info("item --> {}", item);
        assertThat(item).isNotNull();
        assertThat(item.getQuantityAddedToCart()).isEqualTo(2);
        log.info("Cart update obj --> {}", userCart);

        CartResponseDto responseDto = cartService.updateCartItem(updateDto);
        assertThat(responseDto.getCartItems()).isNotNull();
        assertThat(responseDto.getCartItems().get(0)
                .getQuantityAddedToCart()).isEqualTo(3);
    }


}