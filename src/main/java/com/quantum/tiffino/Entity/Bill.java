package com.quantum.tiffino.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "billing_date_time", nullable = false)
    private LocalDateTime billingDateTime = LocalDateTime.now();

    @Column(name = "total_gross_value", nullable = false)
    private double totalGrossValue;

    @Column(name = "total_net_value", nullable = false)
    private double totalNetValue;

    @Column(name = "total_discount", nullable = false)
    private double totalDiscount;

    @Column(name = "delivery_charges", nullable = false)
    private double deliveryCharges;

    @Column(name = "platform_charges", nullable = false)
    private double platformCharges;

    @Column(name = "gst", nullable = false)
    private double gst;

    @Column(name = "grand_total", nullable = false)
    private double grandTotal;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBillingDateTime() {
        return billingDateTime;
    }

    public void setBillingDateTime(LocalDateTime billingDateTime) {
        this.billingDateTime = billingDateTime;
    }

    public double getTotalGrossValue() {
        return totalGrossValue;
    }

    public void setTotalGrossValue(double totalGrossValue) {
        this.totalGrossValue = totalGrossValue;
    }

    public double getTotalNetValue() {
        return totalNetValue;
    }

    public void setTotalNetValue(double totalNetValue) {
        this.totalNetValue = totalNetValue;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public double getPlatformCharges() {
        return platformCharges;
    }

    public void setPlatformCharges(double platformCharges) {
        this.platformCharges = platformCharges;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


