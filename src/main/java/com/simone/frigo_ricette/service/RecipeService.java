package com.simone.frigo_ricette.service;

import com.simone.frigo_ricette.entity.Ingredient;
import com.simone.frigo_ricette.entity.Recipe;
import com.simone.frigo_ricette.repository.IngredientRepository;
import com.simone.frigo_ricette.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final GroceryService groceryService;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public Recipe findById(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("No recipe found with id" + recipeId));
    }

    public Recipe save(Recipe recipe) {
        recipe.setSlug(generateSlug(recipe.getName()));
        return recipeRepository.save(recipe);
    }

    public Recipe update(Long recipeId, Recipe updated) {
        Recipe existing = findById(recipeId);
        existing.setName(updated.getName());
        existing.setSlug(generateSlug(updated.getName()));
        existing.setIngredients(updated.getIngredients());
        existing.setProcess(updated.getProcess());
        return recipeRepository.save(existing);
    }

    public void delete(Long recipeId) {
        recipeRepository.delete(findById(recipeId));
    }

    public void cookRecipe(Long recipeId) {
        Recipe recipe = findById(recipeId);
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getQuantity() < 1) {
                groceryService.addIngredient(ingredient.getId());
            }
        }
    }

    private String generateSlug(String name) {
        return name.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}
