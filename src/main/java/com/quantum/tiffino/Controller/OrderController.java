package com.quantum.tiffino.Controller;

import com.quantum.tiffino.Entity.Order;
import com.quantum.tiffino.Entity.Delivery;
import com.quantum.tiffino.Entity.OrderItem;
import com.quantum.tiffino.Service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    // ✅ Create Order
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        logger.info("Creating order: {}", order);

        // Ensure all order items are linked to the order
        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }

        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    // ✅ Get Order by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        logger.info("Fetching order by ID: {}", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ✅ Get All Orders
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ Update Order Status
    @PutMapping(value = "/{orderId}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        logger.info("Updating order ID {} status to {}", orderId, status);
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }

    // ✅ Assign Delivery
    @PostMapping(value = "/{orderId}/assign-delivery", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Delivery> assignDeliveryToOrder(@PathVariable Long orderId) {
        logger.info("Assigning delivery to order ID {}", orderId);
        return ResponseEntity.ok(orderService.assignDeliveryToOrder(orderId));
    }

    // ✅ Accept Order
    @PutMapping("/{orderId}/accept")
    public ResponseEntity<Void> acceptOrder(@PathVariable Long orderId) {
        logger.info("Accepting order ID {}", orderId);
        orderService.acceptOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // ✅ Reject Order
    @PutMapping("/{orderId}/reject")
    public ResponseEntity<Void> rejectOrder(@PathVariable Long orderId) {
        logger.info("Rejecting order ID {}", orderId);
        orderService.rejectOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // ✅ Get Order Items
    @GetMapping(value = "/{orderId}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderItem>> getOrderItems(@PathVariable Long orderId) {
        logger.info("Fetching order items for order ID {}", orderId);
        return ResponseEntity.ok(orderService.getOrderItems(orderId));
    }

    // ✅ Cancel Order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        logger.info("Cancelling order ID {}", orderId);
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
