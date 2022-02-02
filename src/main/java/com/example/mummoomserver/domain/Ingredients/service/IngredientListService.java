package com.example.mummoomserver.domain.Ingredients.service;
import com.example.mummoomserver.domain.Ingredients.dto.IngredientDto;
import com.example.mummoomserver.domain.Ingredients.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientListService {

    private final IngredientRepository ingredientRepository;

    public List<IngredientDto> getList(String category){
        List<IngredientDto> ret = ingredientRepository.findIngredientByCategory(category);
        return ret;
    }

    public List<IngredientDto> simpleList(int level){
        List<IngredientDto> ret;

        if(level == 1){
                ret = ingredientRepository.findIngredientByScoreBetween(2,4);
        }
        else {
                ret = ingredientRepository.findIngredientByScore(5);
        }
        log.info("리스트 empty? => {}",ret.isEmpty());
        return ret;
    }
}
