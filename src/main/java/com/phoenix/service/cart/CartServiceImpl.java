package com.phoenix.service.cart;

import com.github.fge.jsonpatch.JsonPatch;
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
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.ProductDoesNotExistException;
import com.phoenix.web.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Slf4j
public class CartServiceImpl implements CartService {


    @Autowired
    CartRepository cartRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ProductRepository productRepository;


    @Override
    public CartResponseDto addItemToCart(CartRequestDto cartRequestDto) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {

        //check if user exist
        Optional<AppUser> query = appUserRepository.
                findById(cartRequestDto.getUserId());

        if(query.isEmpty()){
            throw new UserNotFoundException("User with ID "+
                    cartRequestDto.getUserId()+" not found");
        }
        AppUser existingUser = query.get();

        //get user cart
        Cart myCart = existingUser.getMyCart();
        //check product exists
        Product product = productRepository.findById(cartRequestDto.getProductId()).orElse(null);
        if(product == null){
            throw new ProductDoesNotExistException("Product with ID "+ cartRequestDto.getProductId() +" Does not exist");
        }

        if(!quantityIsValid(product, cartRequestDto.getQuantity())){
            throw new BusinessLogicException("Quantity too large");
        }

        log.info("Cart Request dto --> {}", cartRequestDto);
        //add product to cart
        Item cartItem = Item.builder()
                .product(product)
                .quantityAddedToCart(cartRequestDto.getQuantity()).build();
        log.info("Item object --> {}", cartItem);

        myCart.addItem(cartItem);
        myCart.setTotalPrice(myCart.getTotalPrice() + calculateItemPrice(cartItem));
        //save cart
        myCart = cartRepository.save(myCart);

        return buildCartResponse(myCart);
    }

    private Double calculateItemPrice(Item item){
        return item.getProduct().getPrice() * item.getQuantityAddedToCart();
    }
    private CartResponseDto buildCartResponse(Cart cart){
        return CartResponseDto.builder()
                .cartItems(cart.getItemList())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    private boolean quantityIsValid(Product product, int quantity){
        return product.getQuantity() >= quantity;
    }

    @Override
    public CartResponseDto viewCart(Long userId) throws UserNotFoundException {

        AppUser appUser = appUserRepository.findById(userId).orElse(null);
        if(appUser == null){
            throw new UserNotFoundException("user with ID"+userId+"does not exists");
        }
        Cart cart = appUser.getMyCart();
        return buildCartResponse(cart);
    }


    @Override
    public CartResponseDto updateCartItem(CartUpdateDto updateDto) throws UserNotFoundException, BusinessLogicException {

        //get the user by id
        AppUser appUser = appUserRepository.findById(updateDto.getUserId()).orElse(null);
        if(appUser == null){
            throw new UserNotFoundException("User with ID"
                    +updateDto.getUserId()+"not found");
        }
        //get user cart
        Cart myCart = appUser.getMyCart();
        //find item in cart
        Item item = findCartItem(updateDto.getItemId(), myCart).orElse(null);
        if(item == null){
            throw new BusinessLogicException("Item not in cart");
        }
        //perform operation
        if(updateDto.getQuantityOp() == QuantityOperation.INCREASE){
            item.setQuantityAddedToCart(item.getQuantityAddedToCart()+1);
            myCart.setTotalPrice(myCart.getTotalPrice() +
                    item.getProduct().getPrice());
        }
        else if(updateDto.getQuantityOp() == QuantityOperation.DECREASE){
            item.setQuantityAddedToCart(item.getQuantityAddedToCart()-1);
            myCart.setTotalPrice(myCart.getTotalPrice() -
                    item.getProduct().getPrice());
        }
        cartRepository.save(myCart);
        return buildCartResponse(myCart);
    }

    private Optional<Item> findCartItem(Long itemId, Cart cart){
        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
        return cart.getItemList().stream().filter(itemPredicate).findFirst();
    }


}
