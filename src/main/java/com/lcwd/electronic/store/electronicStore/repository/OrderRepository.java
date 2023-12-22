package com.lcwd.electronic.store.electronicStore.repository;

import com.lcwd.electronic.store.electronicStore.entities.Order;
import com.lcwd.electronic.store.electronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {


    List<Order> findByUser(User user);
}
