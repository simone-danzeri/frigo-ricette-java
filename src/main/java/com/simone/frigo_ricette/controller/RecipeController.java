package com.simone.frigo_ricette.controller;

import com.simone.frigo_ricette.dto.RecipeRequest;
import com.simone.frigo_ricette.entity.Ingredient;
import com.simone.frigo_ricette.entity.Recipe;
import com.simone.frigo_ricette.repository.IngredientRepository;
import com.simone.frigo_ricette.repository.RecipeRepository;
import com.simone.frigo_ricette.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientRepository ingredientRepository;

    @GetMapping
    public ResponseEntity<List<Recipe>> findAll() {
        return ResponseEntity.ok(recipeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Recipe> create(@Valid @RequestBody RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setIngredients(resolveIngredients(request.getIngredientsIds()));
        recipe.setProcess(request.getProcess());
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.save(recipe));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @Valid @RequestBody RecipeRequest request) {
        Recipe updated = new Recipe();
        updated.setName(request.getName());
        updated.setProcess(request.getProcess());
        updated.setIngredients(resolveIngredients(request.getIngredientsIds()));
        return ResponseEntity.ok(recipeService.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cook")
    public ResponseEntity<Void> cook(@PathVariable Long id) {
        recipeService.cookRecipe(id);
        return ResponseEntity.noContent().build();
    }

    private List<Ingredient> resolveIngredients(List<Long> ids) {
        return ids.stream()
                .map(id -> ingredientRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id: " + id)))
                .toList();
    }
}
