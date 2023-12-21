package com.lcwd.electronic.store.electronicStore.controller;

import com.lcwd.electronic.store.electronicStore.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.electronicStore.dtos.ApiResponce;
import com.lcwd.electronic.store.electronicStore.dtos.CartDto;
import com.lcwd.electronic.store.electronicStore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    //add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponce> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponce response = ApiResponce.builder()
                .message("Item is removed !!")
                .status(HttpStatus.OK)
                .sucess(true)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponce> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponce response = ApiResponce.builder()
                .message("Now cart is blank !!")
                .sucess(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //add items to cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}