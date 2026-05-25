package com.simone.frigo_ricette.repository;

import com.simone.frigo_ricette.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findBySlug(String slug);
    boolean existsByName(String name);
}
