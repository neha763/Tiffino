package com.quantum.tiffino.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MealPlan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;//(breakfast,lunch,dinner)
        private String description;

        @Column(nullable = false)
        private double price;

//        @OneToMany(mappedBy = "mealPlan")
//        private List<MealCustomization> mealCustomizations;

        @ManyToMany
        @JoinTable(
                name = "meal_plan_customization",
                joinColumns = @JoinColumn(name = "meal_plan_id"),
                inverseJoinColumns = @JoinColumn(name = "meal_customization_id")
        )
        private List<MealCustomization> mealCustomizations;


//        @OneToMany(mappedBy = "mealPlan")
//        private List<Subscription> subscriptions;
//
//        @ManyToOne(cascade = CascadeType.ALL)
//        @JoinColumn(name = "ingredient_id")
//        private Ingredient ingredient;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "menu_id")
        private Menu menu;

        @Enumerated(EnumType.STRING)
        private MealPlanType mealPlanType;

        public MealPlan() {
        }

        public MealPlan(Long id, String name, String description, double price, List<MealCustomization> mealCustomizations, Menu menu, MealPlanType mealPlanType) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
                this.mealCustomizations = mealCustomizations;
                this.menu = menu;
                this.mealPlanType = mealPlanType;
        }

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

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public List<MealCustomization> getMealCustomizations() {
                return mealCustomizations;
        }

        public void setMealCustomizations(List<MealCustomization> mealCustomizations) {
                this.mealCustomizations = mealCustomizations;
        }

        public Menu getMenu() {
                return menu;
        }

        public void setMenu(Menu menu) {
                this.menu = menu;
        }

        public MealPlanType getMealPlanType() {
                return mealPlanType;
        }

        public void setMealPlanType(MealPlanType mealPlanType) {
                this.mealPlanType = mealPlanType;
        }

        @Override
        public String toString() {
                return "MealPlan{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", description='" + description + '\'' +
                        ", price=" + price +
                        ", mealCustomizations=" + mealCustomizations +
                        ", menu=" + menu +
                        ", mealPlanType=" + mealPlanType +
                        '}';
        }
}