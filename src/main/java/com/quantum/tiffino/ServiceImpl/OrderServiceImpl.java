package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.*;
import com.quantum.tiffino.Repository.OrderRepository;
import com.quantum.tiffino.Repository.DeliveryRepository;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryPersonRepository deliveryPersonRepository;



    @Override
    public Order createOrder(Order order) {
        // You can customize here, such as setting order status to "PENDING"
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status));
        return orderRepository.save(order);
    }

    @Override
    public Delivery assignDeliveryToOrder(Long orderId, Long deliveryPersonId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId).orElseThrow(() -> new RuntimeException("DeliveryPerson not found"));

        // Create and assign a delivery
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryPerson(deliveryPerson);
        delivery.setDeliveryStatus(DeliveryStatus.PENDING); // Assuming default status is PENDING
        deliveryRepository.save(delivery);

        return delivery;
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getOrderItems();
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
