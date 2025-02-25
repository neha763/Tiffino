package com.quantum.tiffino.ServiceImpl;

import com.quantum.tiffino.Entity.Ingredient;
import com.quantum.tiffino.Repository.IngredientRepository;
import com.quantum.tiffino.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Optional<Ingredient> getIngredientById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        if (ingredientRepository.existsById(id)) {
            ingredient.setId(id);
            return ingredientRepository.save(ingredient);
        } else {
            throw new IllegalArgumentException("Ingredient not found");
        }
    }

    @Override
    public void deleteIngredient(Long id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Ingredient not found");
        }
    }
}
