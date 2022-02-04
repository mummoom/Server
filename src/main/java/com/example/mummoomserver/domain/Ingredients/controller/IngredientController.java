package com.example.mummoomserver.domain.Ingredients.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.service.IngredientListService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IngredientController {

    private final IngredientListService ingredientListService;

    /**
     * category 별로 재료 보여줌
     */
    @GetMapping("/api/ingredients/{category}")
    public ResponseTemplate<List<Ingredients>> getIngredientList(@PathVariable(name ="category") String category){
        List<Ingredients> ret = ingredientListService.getList(category);
        return new ResponseTemplate<>(ret);

    }


    /**
     *
     * 간단히 알아보는 음식 정보
     */
    @ApiOperation(
            value = "간단히 알아보는 음식 정보 "
            , notes = "도형 표시 로 마크된 간단히 알아보는 음식 정보 리스트를 불러온다  0 = 엑스표시, 1 = 세모표시 , 2 = 동그라미 표시  ")
    @GetMapping("/api/simple/ingredients/{level}")
    public ResponseTemplate<List<Ingredients>> getSimpleIngredientX(@PathVariable(name="level") int level) throws ResponeException {

        if( 0<= level &&level <=2 )  return new ResponseTemplate<>(ingredientListService.simpleList(level));
        else{
            log.info("level 에러!");
            throw  new ResponeException(ResponseTemplateStatus.FAIL);
        }


    }



}
