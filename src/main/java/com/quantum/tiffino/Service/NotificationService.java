package com.quantum.tiffino.Service;



import com.quantum.tiffino.Entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public void sendOrderAssignmentNotification(Long deliveryPersonId, Order order) {
        messagingTemplate.convertAndSend("/topic/delivery/" + deliveryPersonId,
                "New Order Assigned: Order ID " + order.getId());
    }

    // Notify user & restaurant when order status updates
    public void sendOrderUpdateNotification(Order order) {
        messagingTemplate.convertAndSend("/topic/order/" + order.getId(),
                "Order Status Updated: " + order.getOrderStatus());
    }
}
