package com.example.mummoomserver.domain.Ingredients.service;

import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientListService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredients> getList(String category){
        List<Ingredients> ret = ingredientRepository.findIngredientByCategory(category);
        return ret;
    }

    public List<Ingredients> simpleList(int level){
        List<Ingredients> ret;
        if(level == 1){ //세모
                ret = ingredientRepository.findIngredientByScoreBetween(2,4);
        }
        else if(level == 2){ //동그라미
                ret = ingredientRepository.findIngredientByScore(5);
        }
        else { //엑스
            ret = ingredientRepository.findIngredientByScoreBetween(0,1);
        }

        log.info("리스트 empty? => {}",ret.isEmpty());
        return ret;
    }
}
