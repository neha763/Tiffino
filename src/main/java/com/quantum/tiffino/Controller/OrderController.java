package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Order;
import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.OrderItem;
import com.quantum.tiffino.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Update order status
    @PutMapping("/{orderId}/status/{status}")
    public Order updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // Assign delivery person to an order
    @PostMapping("/{orderId}/assign-delivery/{deliveryPersonId}")
    public Delivery assignDeliveryToOrder(@PathVariable Long orderId, @PathVariable Long deliveryPersonId) {
        return orderService.assignDeliveryToOrder(orderId, deliveryPersonId);
    }

    // Get items in an order
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    // Cancel an order
    @PutMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }
}
