package com.quantum.tiffino.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;



@Entity
public class MealCustomization {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "meal_plan_id")
    private MealPlan mealPlan;

    @Enumerated(EnumType.STRING)
    private MealPlanType mealPlanType;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PortionSize portionSize;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SpiceLevel spiceLevel;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DietPlan dietPlan;

    private boolean nonvegetarian;
    private boolean vegetarian;
    private boolean vegan;
    private boolean glutenFree;
    private boolean dairyFree;

    @Column
    private Integer calories;


    public MealCustomization(Long id, User user, MealPlan mealPlan, MealPlanType mealPlanType, Order order, PortionSize portionSize, SpiceLevel spiceLevel, DietPlan dietPlan, boolean nonvegetarian, boolean vegetarian, boolean vegan, boolean glutenFree, boolean dairyFree, Integer calories) {
        this.id = id;
        this.user = user;
        this.mealPlan = mealPlan;
        this.mealPlanType = mealPlanType;
        this.order = order;
        this.portionSize = portionSize;
        this.spiceLevel = spiceLevel;
        this.dietPlan = dietPlan;
        this.nonvegetarian = nonvegetarian;
        this.vegetarian = vegetarian;
        this.vegan = vegan;
        this.glutenFree = glutenFree;
        this.dairyFree = dairyFree;
        this.calories = calories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public MealPlanType getMealPlanType() {
        return mealPlanType;
    }

    public void setMealPlanType(MealPlanType mealPlanType) {
        this.mealPlanType = mealPlanType;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public @NotNull PortionSize getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(@NotNull PortionSize portionSize) {
        this.portionSize = portionSize;
    }

    public @NotNull SpiceLevel getSpiceLevel() {
        return spiceLevel;
    }

    public void setSpiceLevel(@NotNull SpiceLevel spiceLevel) {
        this.spiceLevel = spiceLevel;
    }

    public @NotNull DietPlan getDietPlan() {
        return dietPlan;
    }

    public void setDietPlan(@NotNull DietPlan dietPlan) {
        this.dietPlan = dietPlan;
    }

    public boolean isNonvegetarian() {
        return nonvegetarian;
    }

    public void setNonvegetarian(boolean nonvegetarian) {
        this.nonvegetarian = nonvegetarian;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public void setDairyFree(boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MealCustomization{" +
                "id=" + id +
                ", user=" + user +
                ", mealPlan=" + mealPlan +
                ", mealPlanType=" + mealPlanType +
                ", order=" + order +
                ", portionSize=" + portionSize +
                ", spiceLevel=" + spiceLevel +
                ", dietPlan=" + dietPlan +
                ", nonvegetarian=" + nonvegetarian +
                ", vegetarian=" + vegetarian +
                ", vegan=" + vegan +
                ", glutenFree=" + glutenFree +
                ", dairyFree=" + dairyFree +
                ", calories=" + calories +
                '}';
    }
}