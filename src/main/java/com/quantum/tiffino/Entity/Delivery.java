package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data


@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50) // Ensure enough space
    private DeliveryStatus deliveryStatus;


    private String deliveryAddress;
    private LocalDateTime deliveryTime;
    private LocalDateTime estimatedDeliveryTime;
    private double deliveryFee;
    private String deliveryMethod;
    private String currentLocation;
    private String feedback;
    private int greenPointsEarned;
    private String ticketId;
    private LocalDateTime assignedTime;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    // @JsonBackReference
    private DeliveryPerson deliveryPerson;


    @JsonBackReference("order-delivery")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public Delivery() {
    }

    public Delivery(Long deliveryId, DeliveryStatus deliveryStatus, String deliveryAddress, LocalDateTime deliveryTime, LocalDateTime estimatedDeliveryTime, double deliveryFee, String deliveryMethod, String currentLocation, String feedback, int greenPointsEarned, String ticketId, LocalDateTime assignedTime, DeliveryPerson deliveryPerson, Order order) {
        this.deliveryId = deliveryId;
        this.deliveryStatus = deliveryStatus;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = deliveryTime;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.deliveryFee = deliveryFee;
        this.deliveryMethod = deliveryMethod;
        this.currentLocation = currentLocation;
        this.feedback = feedback;
        this.greenPointsEarned = greenPointsEarned;
        this.ticketId = ticketId;
        this.assignedTime = assignedTime;
        this.deliveryPerson = deliveryPerson;
        this.order = order;
    }

    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getGreenPointsEarned() {
        return greenPointsEarned;
    }

    public void setGreenPointsEarned(int greenPointsEarned) {
        this.greenPointsEarned = greenPointsEarned;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(LocalDateTime assignedTime) {
        this.assignedTime = assignedTime;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryId=" + deliveryId +
                ", deliveryStatus=" + deliveryStatus +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", estimatedDeliveryTime=" + estimatedDeliveryTime +
                ", deliveryFee=" + deliveryFee +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", currentLocation='" + currentLocation + '\'' +
                ", feedback='" + feedback + '\'' +
                ", greenPointsEarned=" + greenPointsEarned +
                ", ticketId='" + ticketId + '\'' +
                ", assignedTime=" + assignedTime +
                ", deliveryPerson=" + deliveryPerson +
                ", order=" + order +
                '}';
    }
}