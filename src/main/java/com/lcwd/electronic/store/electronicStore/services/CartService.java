package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.electronicStore.dtos.CartDto;


public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart:
    void removeItemFromCart(String userId,int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
