package com.phoenix.service.cart;

import com.phoenix.data.dto.CartRequestDto;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.models.Cart;
import com.phoenix.data.models.Item;
import com.phoenix.data.models.Product;
import com.phoenix.data.repository.AppUserRepository;
import com.phoenix.data.repository.CartRepository;
import com.phoenix.data.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Sql("/db/insert.sql")
class CartServiceImplTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addItemToCart() {

       CartRequestDto cartRequestDto = new CartRequestDto();
       cartRequestDto.setProductId(13L);
       cartRequestDto.setUserId(5011L);
       cartRequestDto.setQuantity(1);

       //check if user exist
        AppUser existingUser = appUserRepository.
                findById(cartRequestDto.getUserId()).orElse(null);
        assertThat(existingUser).isNotNull();
        //get user cart
        Cart myCart = existingUser.getMyCart();
        assertThat(myCart).isNotNull();
        //check product exists
        Product product = productRepository.findById(13L).orElse(null);
        assertThat(product).isNotNull();
        assertThat(product.getQuantity()).isGreaterThanOrEqualTo(cartRequestDto.getQuantity());
        //add product to cart
        Item cartItem = new Item(product, cartRequestDto.getQuantity());
        myCart.addItem(cartItem);
        //save cart
        cartRepository.save(myCart);
        assertThat(myCart.getItemList().size()).isEqualTo(1);
    }
}