package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.electronicStore.dtos.OrderDto;
import com.lcwd.electronic.store.electronicStore.dtos.OrderUpdateRequest;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    OrderDto updateOrder(String orderId, OrderUpdateRequest request);

    //order methods(logic) related to order

}
