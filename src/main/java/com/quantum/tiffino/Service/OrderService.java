package com.quantum.tiffino.Service;

import com.quantum.tiffino.Entity.Order;
import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.OrderItem;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    Order updateOrderStatus(Long orderId, String status);

    Delivery assignDeliveryToOrder(Long orderId, Long deliveryPersonId);

    List<OrderItem> getOrderItems(Long orderId);

    void cancelOrder(Long orderId);
}
