package com.example.mummoomserver.domain.Dog.controller;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplate;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.dto.DogListResponseDto;
import com.example.mummoomserver.domain.Dog.dto.DogSaveResponseDto;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.service.DogService;
import com.example.mummoomserver.login.users.service.UserServiceImpl;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DogController {

    private final DogService dogService;
    private final UserServiceImpl userService;

    /*
    * 강아지 정보 생성
    * */
    @ApiOperation(value = "강아지 정보 추가 API", notes = "강아지 정보(이름, 종, 생년월일, 성별, 중성화 수술 여부) 추가합니다. \n" +
            "성공 시 강아지 index 반환합니다. JWT 토큰 입력해야합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 6000, message = "강아지 이름을 입력해주세요."),
            @ApiResponse(code = 6001, message = "강아지 생년월일을 입력해주세요."),
            @ApiResponse(code = 6002, message = "강아지 종을 입력해주세요."),
            @ApiResponse(code = 6003, message = "강아지 성별을 입력해주세요."),
            @ApiResponse(code = 6004, message = "강아지 중성화 수술 정보를 입력해주세요."),
            @ApiResponse(code = 3000, message = "데이터베이스 요청 에러.")
    })
    @ResponseBody
    @PostMapping("/api/dog/save")
    public ResponseTemplate<DogSaveResponseDto> saveDog(@RequestBody DogDto dogRequest){

        if(dogRequest.getDogName()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_NAME);
        if(dogRequest.getDogBirth()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_BIRTH);
        if(dogRequest.getDogType()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_TYPE);
        if(dogRequest.getDogSex()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_SEX);
        if(dogRequest.getSurgery()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_SURGERY);

        try{
            String email = userService.getAuthUserEmail();
            DogSaveResponseDto dogResponse = dogService.save(dogRequest, email);
            return new ResponseTemplate<>(dogResponse);

        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    /*
     *  강아지 1마리 정보 조회
     * */
    @ApiOperation(value = "강아지 1마리 정보 조회 API", notes = "강아지 index로 강아지 정보(이름, 종, 생년월일, 성별, 중성화 수술 여부) 조회합니다. \n JWT 토큰 입력해야합니다.")
    @ApiImplicitParam(name = "dogIdx", value = "강아지 index")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 요청 에러."),
            @ApiResponse(code = 6005, message = "존재하지 않는 강아지 Index입니다."),
            @ApiResponse(code = 6006, message = "유효하지 않은 유저입니다.")
    })
    @ResponseBody
    @GetMapping("/api/dog/{dogIdx}")
    public ResponseTemplate<DogDto> getDogByIdx(@PathVariable("dogIdx")Long dogIdx){
        try{
            String email = userService.getAuthUserEmail();
            DogDto dogResponse = dogService.getByIdx(dogIdx, email);
            return new ResponseTemplate<>(dogResponse);
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    /*
    *  user 별 강아지 정보 조회"
    * */
    @ApiOperation(value = "유저 별 강아지 정보 조회", notes = "JWT 토큰을 넣어서 요청하면 유저의 강아지들 정보(강아지index, 이름, 종, 생년월일, 성별, 중성화 수술 여부) 리스트가 응답으로 반환됩니다. \n JWT 토큰 입력해야합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공"),
            @ApiResponse(code = 3000, message = "데이터베이스 요청 에러.")
    })
    @ResponseBody
    @GetMapping("api/dog/list")
    public ResponseTemplate<List<DogListResponseDto>>getDogsByUserIdx(){
        try{
            String email = userService.getAuthUserEmail();
            List<DogListResponseDto> dogResponse = dogService.getListByUserIdx(email);
            return new ResponseTemplate<>(dogResponse);
        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }


    /*
    *   강아지 정보 수정
    * */
    @ApiOperation(value = "강아지 정보 수정 API", notes = "강아지 index로 강아지 정보(dogName: 강아지 이름, dogBirth: 강아지 생년월일," +
            " dogSex: 강아지 성별('0' 또는 '1'), dogType: 강아지 종, surgery: 중성화 여부('Y' 또는 'N')) 수정합니다. \n JWT 토큰 입력해야합니다.")
    @ApiImplicitParam(name = "dogIdx", value = "강아지 index")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공 (data=강아지 정보 수정에 성공하였습니다.)"),
            @ApiResponse(code = 6000, message = "강아지 이름을 입력해주세요."),
            @ApiResponse(code = 6001, message = "강아지 생년월일을 입력해주세요."),
            @ApiResponse(code = 6002, message = "강아지 종을 입력해주세요."),
            @ApiResponse(code = 6003, message = "강아지 성별을 입력해주세요."),
            @ApiResponse(code = 6004, message = "강아지 중성화 수술 정보를 입력해주세요."),
            @ApiResponse(code = 3000, message = "데이터베이스 요청 에러."),
            @ApiResponse(code = 6005, message = "존재하지 않는 강아지 Index입니다."),
            @ApiResponse(code = 6006, message = "유효하지 않은 유저입니다.")
    })
    @ResponseBody
    @PatchMapping("/api/dog/update/{dogIdx}")
    public ResponseTemplate<String> updateDog(@PathVariable("dogIdx") Long dogIdx, @RequestBody DogDto dogRequest){

        if(dogRequest.getDogName()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_NAME);
        if(dogRequest.getDogBirth()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_BIRTH);
        if(dogRequest.getDogType()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_TYPE);
        if(dogRequest.getDogSex()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_SEX);
        if(dogRequest.getSurgery()==null) return new ResponseTemplate<>(ResponseTemplateStatus.EMPTY_DOG_SURGERY);

        try{
            String email = userService.getAuthUserEmail();
            dogService.update(dogIdx, dogRequest, email);
            String result = "강아지 정보 수정에 성공하였습니다.";
            return new ResponseTemplate<>(result);

        }catch(ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }

    /*
    *  강아지 정보 삭제
    * */
    @ApiOperation(value = "강아지 정보 삭제 API", notes = "강아지 index로 강아지 정보 삭제합니다. JWT 토큰 입력해야합니다.")
    @ApiImplicitParam(name = "dogIdx", value = "강아지 index")
    @ApiResponses({
            @ApiResponse(code = 200, message = "요청 성공 (data=강아지 정보 삭제에 성공하였습니다.)"),
            @ApiResponse(code = 3000, message = "데이터베이스 요청 에러."),
            @ApiResponse(code = 6005, message = "존재하지 않는 강아지 Index입니다."),
            @ApiResponse(code = 6006, message = "유효하지 않은 유저입니다.")
    })
    @ResponseBody
    @PatchMapping("/api/dog/{dogIdx}")
    public ResponseTemplate<String> deleteDog(@PathVariable("dogIdx")Long dogIdx){
        try{
            String email = userService.getAuthUserEmail();
            dogService.delete(dogIdx, email);
            String result = "강아지 정보 삭제에 성공하였습니다.";
            return new ResponseTemplate<>(result);

        }catch (ResponeException e){
            return new ResponseTemplate<>(e.getStatus());
        }
    }
}
