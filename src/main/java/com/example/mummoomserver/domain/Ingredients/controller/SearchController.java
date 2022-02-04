package com.example.mummoomserver.domain.Ingredients.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Ingredients.dto.IngredientSearchResultDto;
import com.example.mummoomserver.domain.Ingredients.service.SearchService;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchController {


    private final SearchService searchService;
    /**
     *
     * @Query_String name
     * @return 검색 결과
     */
    @GetMapping("/api/search")
    public ResponseTemplate<IngredientSearchResultDto> searchResult(@RequestParam String name){

        try {
            IngredientSearchResultDto result = searchService.getSearchResult(name);
            return new ResponseTemplate<IngredientSearchResultDto>(result);

        }catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }

    }





}
