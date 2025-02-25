package com.quantum.tiffino.Service;




import com.quantum.tiffino.Entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    Ingredient createIngredient(Ingredient ingredient);

    Optional<Ingredient> getIngredientById(Long id);

    List<Ingredient> getAllIngredients();

    Ingredient updateIngredient(Long id, Ingredient ingredient);

    void deleteIngredient(Long id);
}

