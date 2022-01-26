package com.example.mummoomserver.domain.Ingredients.controller;

import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Ingredients.dto.IngredientDto;
import com.example.mummoomserver.domain.Ingredients.service.IngredientListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientListService ingredientListService;

    /**
     * category 별로 재료 보여줌
     //     */
    @GetMapping("/api/ingredients")
    public ResponseTemplate<List<IngredientDto>> getIngredientList(@PathVariable(name ="category") String category){

        List<IngredientDto> ret = ingredientListService.getList(category);

        return new ResponseTemplate<>(ret);

    }

}
