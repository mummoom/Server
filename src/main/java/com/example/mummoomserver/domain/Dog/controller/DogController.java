package com.example.mummoomserver.domain.Dog.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.service.DogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DogController {

    private final DogService dogService;

    /*
    *   강아지 정보 수정
    * */
    @ResponseBody
    @PatchMapping("/api/dog/update/{dogIdx}")
    public ResponseTemplate<String> updateDog(@PathVariable("dogIdx") Long dogIdx, @RequestBody DogDto dogRequest){

        try{
            dogService.update(dogIdx, dogRequest);
            String result = "강아지 정보 수정에 성공하였습니다.";
            return new ResponseTemplate<>(result);

        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    *  강아지 정보 삭제
    * */
    @ResponseBody
    @PatchMapping("api/dog/{dogIdx}")
    public ResponseTemplate<String> deleteDog(@PathVariable("dogIdx")Long dogIdx){
        try{
            dogService.delete(dogIdx);
            String result = "강아지 정보 삭제에 성공하였습니다.";
            return new ResponseTemplate<>(result);

        }catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }
}
