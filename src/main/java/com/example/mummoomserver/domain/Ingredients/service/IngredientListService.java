package com.example.mummoomserver.domain.Ingredients.service;
import com.example.mummoomserver.domain.Ingredients.dto.IngredientDto;
import com.example.mummoomserver.domain.Ingredients.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IngredientListService {

    private IngredientRepository ingredientRepository;

    public List<IngredientDto> getList(String category){
        List<IngredientDto> ret = ingredientRepository.findIngredientByCategory(category);
        return ret;

    }
}
