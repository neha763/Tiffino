package com.quantum.tiffino.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Data
@Getter
@Setter
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private LocalDate startDate;
    private LocalDate endDate;
    //
//    @Lob
//    private List<byte[]> images;
    @Column(length = 500)
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MealPlan> mealPlans;

    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    @ManyToOne
    @JoinColumn(name = "region_id")
    //@JsonIgnoreProperties("menus")
    @JsonIgnore
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
   // @JsonIgnoreProperties("menus")
    @JsonIgnore
    private Restaurant restaurant;

    public Menu()
        {}

    public Menu(Long id, String itemName, LocalDate startDate, LocalDate endDate, String imageUrl, List<MealPlan> mealPlans, CuisineType cuisineType, Region region, Category category, Restaurant restaurant) {
        this.id = id;
        this.itemName = itemName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.mealPlans = mealPlans;
        this.cuisineType = cuisineType;
        this.region = region;
        this.category = category;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(List<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", mealPlans=" + mealPlans +
                ", cuisineType=" + cuisineType +
                ", region=" + region +
                ", category=" + category +
                ", restaurant=" + restaurant +
                '}';
    }
}