package com.quantum.tiffino.Exception;

public class MealPlanNotFoundException extends RuntimeException{
    public MealPlanNotFoundException(String message){
        super(message);
    }
}
