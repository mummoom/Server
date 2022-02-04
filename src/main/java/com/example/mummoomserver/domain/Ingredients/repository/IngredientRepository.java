package com.example.mummoomserver.domain.Ingredients.repository;

import com.example.mummoomserver.domain.Ingredients.dto.IngredientDto;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredients,Long> {

    Optional<Ingredients> findIngredientByName(String name);

    List<Ingredients> findIngredientByCategory(String category);

    List<Ingredients> findIngredientByScoreBetween(int from, int to);

    List<Ingredients> findIngredientByScore(int level);



}
