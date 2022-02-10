package com.example.mummoomserver.domain.Ingredients.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;

import com.example.mummoomserver.domain.Ingredients.dto.IngredientSearchResultDto;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final IngredientRepository ingredientRepository;


    public List<IngredientSearchResultDto> getSearchResult(String name) throws ResponeException {

        try{
            List<Ingredients> ret = ingredientRepository.findIngredientsByNameContains(name);
            log.info("뽑아온 값 {}",ret);
            if(!ret.isEmpty()){
                 List<IngredientSearchResultDto> convertedRet = ret.stream()
                         .map(e -> new IngredientSearchResultDto(e))
                         .collect(Collectors.toList());
                return convertedRet;
            }
            else throw new ResponeException(NO_SEARCH_RESULT);

        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }

    }
}
