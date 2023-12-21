package com.lcwd.electronic.store.electronicStore.repository;

import com.lcwd.electronic.store.electronicStore.entities.Cart;
import com.lcwd.electronic.store.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {


    Optional<Cart> findByUser(User user);

}