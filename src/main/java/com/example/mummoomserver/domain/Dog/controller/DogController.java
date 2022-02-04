package com.example.mummoomserver.domain.Dog.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.dto.DogSaveResponseDto;
import com.example.mummoomserver.domain.Dog.service.DogService;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DogController {

    private final DogService dogService;
    private final UserServiceImpl userService;

    /*
    * 강아지 정보 생성
    * */
    @ApiOperation(value = "강아지 정보 추가 API", notes = "강아지 정보(이름, 종, 생년월일, 성별, 중성화 수술 여부) 추가 " +
            "성공 시 강아지 index 반환")
    @ApiImplicitParam(name = "dogRequest", value = "dogName: 강아지 이름, dogBirth: 강아지 생년월일, " +
                    "dogSex: 강아지 성별('0' 또는 '1'), dogType: 강아지 종, surgery: 중성화 여부('Y' 또는 'N')")
    @ResponseBody
    @PostMapping("/api/dog/save")
    public ResponseTemplate<DogSaveResponseDto> saveDog(@RequestBody DogDto dogRequest){

        try{
            DogSaveResponseDto dogResponse = dogService.save(dogRequest);
            return new ResponseTemplate<>(dogResponse);

        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
     *  강아지 1마리 정보 조회
     * */
    @ApiOperation(value = "강아지 1마리 정보 조회 API", notes = "강아지 index로 강아지 정보(이름, 종, 생년월일, 성별, 중성화 수술 여부) 조회")
    @ApiImplicitParam(name = "dogIdx", value = "강아지 index")
    @ResponseBody
    @GetMapping("/api/dog/{dogIdx}")
    public ResponseTemplate<DogDto> getDogByIdx(@PathVariable("dogIdx")Long dogIdx){
        try{
            DogDto dogResponse = dogService.getByIdx(dogIdx);
            return new ResponseTemplate<>(dogResponse);
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
    *  user 별 강아지 정보 조회
    * */
    //@ResponseBody
    //@GetMapping("api/dog/{userIdx}")
    //public ResponseTemplate<List<DogDto>>getDogsByUserIdx(@PathVariable("userIdx")Long userIdx){

    //}


    /*
    *   강아지 정보 수정
    * */
    @ApiOperation(value = "강아지 정보 수정 API", notes = "강아지 index로 강아지 정보 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dogIdx", value = "강아지 index"),
            @ApiImplicitParam
                    (name = "dogRequest", value = "dogName: 강아지 이름, dogBirth: 강아지 생년월일, " +
                            "dogSex: 강아지 성별('0' 또는 '1'), dogType: 강아지 종, surgery: 중성화 여부('Y' 또는 'N')")
    })
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
    @ApiOperation(value = "강아지 정보 삭제 API", notes = "강아지 index로 강아지 정보 삭제")
    @ApiImplicitParam(name = "dogIdx", value = "강아지 index")
    @ResponseBody
    @PatchMapping("/api/dog/{dogIdx}")
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
