package com.simone.frigo_ricette.controller;

import com.simone.frigo_ricette.dto.IngredientRequest;
import com.simone.frigo_ricette.entity.Ingredient;
import com.simone.frigo_ricette.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor

public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<Ingredient>> findAll() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Ingredient> create(@Valid @RequestBody IngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setQuantity(request.getQuantity());
        ingredient.setAvailable(request.isAvailable());
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.save(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @Valid @RequestBody IngredientRequest request) {
        Ingredient updated = new Ingredient();
        updated.setName(request.getName());
        updated.setQuantity(request.getQuantity());
        updated.setAvailable(request.isAvailable());
        return ResponseEntity.ok(ingredientService.update(id, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
