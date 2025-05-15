package com.quantum.tiffino.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_and_time")
    @DateTimeFormat
    private LocalDateTime localDateTime = LocalDateTime.now();

    @NotNull(message = "Order status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.PENDING;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference("restaurant-orders")  // Prevents recursion
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "meal_customization_id")
    @JsonIgnore // Prevents deep nesting issue
    private MealCustomization mealCustomization;

    @JsonManagedReference("order-orderItems")
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    //@JsonManagedReference // Allows serialization of this side
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonManagedReference("order-delivery")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> delivery = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus = DeliveryStatus.PENDING;

    @JsonBackReference("user-orders")  // Unique reference name
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @JsonManagedReference("order-subscriptions")
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<Subscription> subscriptions = new ArrayList<>();

    @Column(name = "total_gross_value")
    private double totalGrossValue;

    @Column(name = "total_net_value")
    private double totalNetValue;

    @Column(name = "total_discount")
    private double totalDiscount;

    @Column(name = "delivery_charges")
    private double deliveryCharges;

    @Column(name = "platform_charges")
    private double platformCharges;

    @Column(name = "gst")
    private double gst;

    @Column(name = "grand_total")
    private double grandTotal;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id") // Foreign key in order table
    private DeliveryPerson deliveryPerson;


    public Order() {
    }

    public Order(Long id, LocalDateTime localDateTime, OrderStatus orderStatus, Restaurant restaurant, MealCustomization mealCustomization, List<OrderItem> orderItems, List<Delivery> delivery, User user, double totalGrossValue, double totalNetValue, double totalDiscount, double deliveryCharges, double platformCharges, double gst, double grandTotal) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.orderStatus = orderStatus;
        this.restaurant = restaurant;
        this.mealCustomization = mealCustomization;
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.user = user;
        this.totalGrossValue = totalGrossValue;
        this.totalNetValue = totalNetValue;
        this.totalDiscount = totalDiscount;
        this.deliveryCharges = deliveryCharges;
        this.platformCharges = platformCharges;
        this.gst = gst;
        this.grandTotal = grandTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public @NotNull(message = "Order status cannot be null") OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(@NotNull(message = "Order status cannot be null") OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public MealCustomization getMealCustomization() {
        return mealCustomization;
    }

    public void setMealCustomization(MealCustomization mealCustomization) {
        this.mealCustomization = mealCustomization;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Delivery> delivery) {
        this.delivery = delivery;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                ", orderStatus=" + orderStatus +
                ", restaurant=" + restaurant +
                ", mealCustomization=" + mealCustomization +
                ", orderItems=" + orderItems +
                ", delivery=" + delivery +
                ", user=" + user +
                ", totalGrossValue=" + totalGrossValue +
                ", totalNetValue=" + totalNetValue +
                ", totalDiscount=" + totalDiscount +
                ", deliveryCharges=" + deliveryCharges +
                ", platformCharges=" + platformCharges +
                ", gst=" + gst +
                ", grandTotal=" + grandTotal +
                '}';
    }


}
