package com.simone.frigo_ricette.controller;

import com.simone.frigo_ricette.entity.Grocery;
import com.simone.frigo_ricette.service.GroceryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/groceries")
@RequiredArgsConstructor

public class GroceryController {

    private final GroceryService groceryService;

    @GetMapping
    public ResponseEntity<List<Grocery>> findAll() {
        return ResponseEntity.ok(groceryService.findAll());
    }

    // ADD AN INGREDIENT OR INCREASE ITS QUANTITY
    @PostMapping("/{ingredientId}")
    public ResponseEntity<Grocery> add(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(groceryService.addIngredient(ingredientId));
    }

    @PostMapping("/{ingredientId}/bought")
    public ResponseEntity<Void> markAsBought(@PathVariable Long ingredientId) {
        groceryService.markAsBought(ingredientId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<Void> remove(@PathVariable Long ingredientId) {
        groceryService.removeIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }
}
