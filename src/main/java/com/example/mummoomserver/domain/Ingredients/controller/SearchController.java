package com.example.mummoomserver.domain.Ingredients.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Ingredients.dto.IngredientSearchResultDto;
import com.example.mummoomserver.domain.Ingredients.dto.SearchReqDto;
import com.example.mummoomserver.domain.Ingredients.entity.Ingredients;
import com.example.mummoomserver.domain.Ingredients.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {


    private final SearchService searchService;

    /**
     * @Query_String name
     * @return 검색 결과
     */
    @ApiOperation(
            value = "재료 검색하기 "
            , notes = "IngredientName에 입력된 값으로 검색한 재료들의 리스트를 보여준다. 초성검색 안됨 ")
    @PostMapping("/api/search")
    @ApiResponses({
            @ApiResponse(code = 200, message = "카테고리 검색 성공 "),
            @ApiResponse(code = 4000, message = "검색 결과가 없습니다.")
    })
    public ResponseTemplate<List<Ingredients>> searchResult(@RequestBody SearchReqDto searchReqDto){
        try {
            String IngredientName = searchReqDto.getIngredientName();

            List<Ingredients> result = searchService.getSearchResult(IngredientName);
            return new ResponseTemplate<>(result);

        }catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }

    }





}
