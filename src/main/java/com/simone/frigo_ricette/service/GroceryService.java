package com.simone.frigo_ricette.service;

import com.simone.frigo_ricette.entity.Grocery;
import com.simone.frigo_ricette.entity.Ingredient;
import com.simone.frigo_ricette.repository.GroceryRepository;
import com.simone.frigo_ricette.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroceryService {
    private final GroceryRepository groceryRepository;
    private final IngredientService ingredientService;

    public List<Grocery> findAll() {
        return groceryRepository.findAll();
    }

    public Grocery addIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        return groceryRepository.findByIngredient(ingredient)
                .map(existing -> {
                    existing.setQuantity((short) (existing.getQuantity() + 1));
                    return groceryRepository.save(existing);
                })
                .orElseGet(() -> {
                    Grocery grocery = new Grocery();
                    grocery.setIngredient(ingredient);
                    grocery.setQuantity((short) 1);
                    return groceryRepository.save(grocery);
                });
    }

    public void markAsBought(Long ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        Grocery grocery = groceryRepository.findByIngredient(ingredient)
                .orElseThrow(() -> new RuntimeException("Ingredient not in grocery list yet"));
        groceryRepository.delete(grocery);
        ingredient.setQuantity((short) (ingredient.getQuantity() + grocery.getQuantity()));
        ingredientService.save(ingredient);
    }

    public void removeIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        groceryRepository.findByIngredient(ingredient)
                .ifPresent(groceryRepository::delete);
    }
}
