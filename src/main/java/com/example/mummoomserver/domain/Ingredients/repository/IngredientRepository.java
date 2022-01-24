package com.example.mummoomserver.domain.Ingredients.repository;

import com.example.mummoomserver.domain.Ingredients.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    Optional<Ingredient> findIngredientByName(String name);

}
