package com.example.mummoomserver.domain.Ingredients.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;

import com.example.mummoomserver.domain.Ingredients.dto.IngredientSearchResultDto;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final IngredientRepository ingredientRepository;


    public IngredientSearchResultDto getSearchResult(String name) throws ResponeException {

        try{
            Optional<Ingredients> ret = ingredientRepository.findIngredientByName(name);
            return new IngredientSearchResultDto(ret.orElse(null));

        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }

    }
}
