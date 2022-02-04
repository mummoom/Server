package com.example.mummoomserver.domain.Dog.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.dto.DogSaveResponseDto;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.repository.DogRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.DATABASE_ERROR;
import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.FAIL;

@Service
@Slf4j
@RequiredArgsConstructor
public class DogService {

    private final DogRepository dogRepository;
    private final UserRepository userRepository;

    //강아지 정보 추가
    public DogSaveResponseDto save(DogDto dogRequest, String nickname) throws ResponeException {
        User user = userRepository.findByNickName(nickname).get();

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
    public DogDto getByIdx(Long dogIdx) throws ResponeException{
        Optional<Dog> optionalDog = dogRepository.findById(dogIdx);

        try{
            if (optionalDog.isPresent()) {
                Dog dog = optionalDog.get();
                return new DogDto(dog.getDogName(), dog.getDogBirth(), dog.getDogType(), dog.getDogSex(),dog.getSurgery());
            } else {
                throw new ResponeException(FAIL);
            }
        }catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //강아지 정보 수정
    public void update(Long dogIdx, DogDto dogRequest) throws ResponeException {
        Optional<Dog> dog =  dogRepository.findById(dogIdx);

        try {
            if (dog.isPresent()) {
                Dog updateDog = dog.get();
                updateDog.update(dogRequest.getDogName(), dogRequest.getDogBirth(), dogRequest.getDogType(), dogRequest.getDogSex(), dogRequest.getSurgery());
                dogRepository.save(updateDog);
            } else {
                throw new ResponeException(FAIL);
            }
        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    //강아지 정보 삭제
    public void delete(Long dogIdx) throws ResponeException {
        Optional<Dog> dog =  dogRepository.findById(dogIdx);

        try {
            if (dog.isPresent()) {
                dog.get().delete();
                dogRepository.save(dog.get());
            } else {
                throw new ResponeException(FAIL);
            }
        }catch (Exception e){
            log.info("Search Service log = {}",e.getMessage());
            throw new ResponeException(DATABASE_ERROR);
        }
    }
}
