package com.quantum.tiffino.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Name of the ingredient (e.g., "Tomato", "Cumin", "Chili Powder")
    private String name;

    // Type of ingredient (e.g., "Spice", "Vegetable", "Meat", "Grain")
    private String type;

    // Many-to-many relationship with Meal (a meal can have many ingredients and vice versa)
//    @ManyToMany(mappedBy = "ingredient")
//    private List<MealPlan> mealPlan;  // Meals that use this ingredient

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public List<MealPlan> getMealPlan() {
//        return mealPlan;
//    }
//
//    public void setMealPlan(List<MealPlan> mealPlan) {
//        this.mealPlan = mealPlan;
//    }
//}
}
