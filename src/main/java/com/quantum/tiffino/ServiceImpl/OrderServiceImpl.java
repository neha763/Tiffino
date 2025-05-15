package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.*;
import com.quantum.tiffino.Repository.OrderRepository;
import com.quantum.tiffino.Repository.DeliveryRepository;
import com.quantum.tiffino.Repository.DeliveryPersonRepository;
import com.quantum.tiffino.Service.NotificationService;
import com.quantum.tiffino.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    @Autowired
    private NotificationService notificationService;

    @Override
    public Order createOrder(Order order) {
        order.setOrderStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        notificationService.sendOrderUpdateNotification(order);
        return order;
    }

    public Delivery assignDeliveryToOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<DeliveryPerson> availableDeliveryPersons = deliveryPersonRepository.findByAvailable(true);
        if (availableDeliveryPersons.isEmpty()) {
            throw new RuntimeException("No delivery person available");
        }

        DeliveryPerson deliveryPerson = availableDeliveryPersons.stream()
                .min(Comparator.comparingDouble(dp -> calculateDistance(order, dp)))
                .orElseThrow(() -> new RuntimeException("No suitable delivery person found"));

        deliveryPerson.setAvailable(false);

        Delivery newDelivery = new Delivery();
        newDelivery.setOrder(order);
        newDelivery.setDeliveryPerson(deliveryPerson);
        newDelivery.setDeliveryStatus(DeliveryStatus.ASSIGNED);
        order.getDelivery().add(newDelivery);

        orderRepository.save(order);
        deliveryPersonRepository.save(deliveryPerson);

        notificationService.sendOrderAssignmentNotification(deliveryPerson.getId(), order);
        return newDelivery;
    }

    @Scheduled(fixedRate = 60000)
    public void reassignUnacceptedOrders() {
        List<Delivery> unacceptedDeliveries = deliveryRepository.findByDeliveryStatus(DeliveryStatus.ASSIGNED);
        for (Delivery delivery : unacceptedDeliveries) {
            if (delivery.getAssignedTime().plusMinutes(1).isBefore(java.time.LocalDateTime.now())) {
                rejectOrder(delivery.getOrder().getId());
            }
        }
    }

    public void acceptOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Delivery delivery = order.getDelivery().get(0);
        delivery.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
        order.setOrderStatus(OrderStatus.IN_TRANSIT);

        orderRepository.save(order);
        notificationService.sendOrderUpdateNotification(order);
    }

    public void rejectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Delivery delivery = order.getDelivery().get(0);
        DeliveryPerson previousPerson = delivery.getDeliveryPerson();
        previousPerson.setAvailable(true);

        List<DeliveryPerson> availablePersons = deliveryPersonRepository.findByAvailable(true);
        if (availablePersons.isEmpty()) {
            throw new RuntimeException("No available delivery person to reassign");
        }

        DeliveryPerson newPerson = availablePersons.stream()
                .min(Comparator.comparingDouble(dp -> calculateDistance(order, dp)))
                .orElseThrow(() -> new RuntimeException("No suitable delivery person found"));

        delivery.setDeliveryPerson(newPerson);
        newPerson.setAvailable(false);

        orderRepository.save(order);
        deliveryPersonRepository.save(newPerson);
        deliveryPersonRepository.save(previousPerson);

        notificationService.sendOrderAssignmentNotification(newPerson.getId(), order);
    }

    @Override
    public List<OrderItem> getOrderItems(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getOrderItems();
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        notificationService.sendOrderUpdateNotification(order);
    }

    private double calculateDistance(Order order, DeliveryPerson dp) {
        return Math.random() * 10;
    }
}
