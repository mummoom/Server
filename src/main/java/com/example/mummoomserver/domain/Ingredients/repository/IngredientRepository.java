package com.example.mummoomserver.domain.Ingredients.repository;

import com.example.mummoomserver.domain.Ingredients.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {


}
