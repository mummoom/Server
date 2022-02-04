package com.example.mummoomserver.domain.Ingredients.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Ingredients.Category;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.service.IngredientListService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiOperation(
            value = "category 별 재료 정보 보여주기 "
            , notes = "카테고리별 재료를 표시해준다  0 = 채소 , 1 = 생선 , 2 = 고기, 3 = 유제품 , 4 = 어패류 ,5 = 곡류, 6 = 과일 , 7 = 기타 ")
    @GetMapping("/api/ingredients/{categoryNum}")
    @ApiImplicitParam(name ="categoryNum", value="카테고리 번호, int 값", required = true)
    public ResponseTemplate<List<Ingredients>> getIngredientList(@PathVariable(name ="categoryNum") int categoryNum){

        try {
            String categoryName = Category.getCategoryNameByNum(categoryNum); // categorynum에 따라 name을 추출해낸다
            List<Ingredients> ret = ingredientListService.getList(categoryName); //repository에 쿼리를 날린다
            return new ResponseTemplate<>(ret);
        }catch (IllegalArgumentException e){
            return new ResponseTemplate<>(ResponseTemplateStatus.INVALID_PARAM);
        }

    }


    /**
     *
     * 간단히 알아보는 음식 정보
     */
    @ApiOperation(
            value = "간단히 알아보는 음식 정보 "
            , notes = "도형 표시 로 마크된 간단히 알아보는 음식 정보 리스트를 불러온다  0 = 엑스표시, 1 = 세모표시 , 2 = 동그라미 표시  ")
    @GetMapping("/api/simple/ingredients/{level}")
    @ApiImplicitParam(name ="level", value="0 ,1 , 2 중 하나의 값이 level이 된다", required = true)
    public ResponseTemplate<List<Ingredients>> getSimpleIngredientX(@PathVariable(name="level") int level) throws ResponeException {

        if( 0<= level &&level <=2 )  return new ResponseTemplate<>(ingredientListService.simpleList(level));
        else{   //그 외는 레벨이 안됨
            log.info("level 에러!");
            throw  new ResponeException(ResponseTemplateStatus.FAIL);
        }


    }



}
