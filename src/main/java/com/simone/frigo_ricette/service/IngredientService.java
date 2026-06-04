package com.simone.frigo_ricette.service;

import com.simone.frigo_ricette.entity.Ingredient;
import com.simone.frigo_ricette.repository.GroceryRepository;
import com.simone.frigo_ricette.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found with id:" + id));
    }

    public Ingredient save(Ingredient ingredient) {
        ingredient.setSlug(generateSlug(ingredient.getName()));
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(Long id, Ingredient updated) {
        Ingredient existing = findById(id);
        existing.setName(updated.getName());
        existing.setQuantity(updated.getQuantity());
        existing.setAvailable(updated.isAvailable());
        existing.setSlug(generateSlug(updated.getName()));
        return ingredientRepository.save(existing);
    }

    public void delete(Long id) {
        Ingredient ingredient = findById(id);
        ingredientRepository.delete(ingredient);
    }

    private String generateSlug(String name) {
        return name.toLowerCase().trim().replaceAll("\\s+", "-");
    }

}
