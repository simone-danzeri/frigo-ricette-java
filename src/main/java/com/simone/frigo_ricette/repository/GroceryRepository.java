package com.simone.frigo_ricette.repository;

import com.simone.frigo_ricette.entity.Grocery;
import com.simone.frigo_ricette.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery, Long> {
    boolean existsByIngredient(Ingredient ingredient);
    Optional<Grocery> findByIngredient(Ingredient ingredient);
}
