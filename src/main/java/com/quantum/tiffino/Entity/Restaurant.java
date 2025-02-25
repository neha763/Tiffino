package com.quantum.tiffino.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location; // Location or address of the restaurant/cloud kitchen

    private String description; // Brief description of the restaurant

    private String contactNumber; // Contact number for the restaurant

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType; // Enum to store cuisine type (e.g., Punjabi, South Indian, Bengali, etc.)

    @Column(nullable = false)
    private Boolean isActive = true; // Is the restaurant currently active and delivering?

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Menu> menus; // List of menu items served by the restaurant

    @OneToMany(mappedBy = "id")
    private List<MealPlan> mealPlans;

    private String hygieneStatus;

    @ManyToMany(mappedBy = "restaurants")
    private List<User> users;

//
//    @OneToMany(mappedBy = "restaurant")
//    private List<Order> order;





    @ManyToMany
    @JoinTable(
            name = "restaurant_delivery_person",
            joinColumns = @JoinColumn(name="restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "delivery_person_id")
    )
    private List<DeliveryPerson> deliveryPerson; // Delivery partner managing delivery logistics (Zomato, Swiggy, etc.)

    private Double rating;// Ratings for the restaurant by customers

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public String getHygieneStatus() {
        return hygieneStatus;
    }

    public void setHygieneStatus(String hygieneStatus) {
        this.hygieneStatus = hygieneStatus;
    }

    public List<DeliveryPerson> getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(List<DeliveryPerson> deliveryPerson) {
        this.deliveryPerson= deliveryPerson;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

//    public List<Order> getOrder() {
//        return orders;
//    }
//
//    public void setOrder(List<Order> orders) {
//        this.orders = order;
//    }
}