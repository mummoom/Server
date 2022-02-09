package com.example.mummoomserver.domain.Dog.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.dto.DogListResponseDto;
import com.example.mummoomserver.domain.Dog.dto.DogSaveResponseDto;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.repository.DogRepository;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final UserRepository userRepository;

    //강아지 정보 추가
    public DogSaveResponseDto save(DogDto dogRequest, String email) throws ResponeException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        try {
            Dog dog = Dog.builder()
                    .user(user)
                    .dogName(dogRequest.getDogName())
                    .dogBirth(dogRequest.getDogBirth())
                    .dogType(dogRequest.getDogType())
                    .dogSex(dogRequest.getDogSex())
                    .surgery(dogRequest.getSurgery())
                    .status("active")
                    .build();

            return new DogSaveResponseDto(dogRepository.save(dog).getDogIdx());
        }catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //강아지 1마리 정보 조회
    public DogDto getByIdx(Long dogIdx, String email) throws ResponeException{
        Dog dog = dogRepository.findById(dogIdx).orElseThrow(() -> new ResponeException(INVALID_DOG_INDEX));

        if(dog.getStatus() != "active") throw new ResponeException(INVALID_DOG_INDEX);
        if(email.compareTo(dog.getUser().getEmail())!=0 ) throw new ResponeException(INVALID_DOG_USER);

        try{
            return new DogDto(dog.getDogName(), dog.getDogBirth(), dog.getDogType(), dog.getDogSex(),dog.getSurgery());
        }catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //유저 별 강아지 정보 조회
    public List<DogListResponseDto> getListByUserIdx(String email) throws ResponeException{
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        try {
            List<Dog> dogs = dogRepository.findDogsByUser_userIdxAndStatus(user.getUserIdx(), "active");

            List<DogListResponseDto> dogListResDto = new ArrayList<>();
            for (int i = 0; i < dogs.size(); i++){
                DogListResponseDto dogResDto = new DogListResponseDto(dogs.get(i));
                dogListResDto.add(dogResDto);
            }

            return dogListResDto;
        }catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //강아지 정보 수정
    public void update(Long dogIdx, DogDto dogRequest, String email) throws ResponeException {
        Dog dog = dogRepository.findById(dogIdx).orElseThrow(() -> new ResponeException(INVALID_DOG_INDEX));

        if(email.compareTo(dog.getUser().getEmail())!=0) throw new ResponeException(INVALID_DOG_USER);

        try {
            dog.update(dogRequest.getDogName(), dogRequest.getDogBirth(), dogRequest.getDogType(), dogRequest.getDogSex(), dogRequest.getSurgery());
            dogRepository.save(dog);
        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //강아지 정보 삭제
    public void delete(Long dogIdx, String email) throws ResponeException {
        Dog dog = dogRepository.findById(dogIdx).orElseThrow(() -> new ResponeException(INVALID_DOG_INDEX));

        if(email.compareTo(dog.getUser().getEmail())!=0) throw new ResponeException(INVALID_DOG_USER);

        try {
            dog.delete();
            dogRepository.save(dog);
        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }
    }
}
