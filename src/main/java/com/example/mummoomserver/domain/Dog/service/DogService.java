package com.example.mummoomserver.domain.Dog.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Dog.dto.DogDto;
import com.example.mummoomserver.domain.Dog.entity.Dog;
import com.example.mummoomserver.domain.Dog.repository.DogRepository;
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


    //강아지 정보 수정
    public void update(Long dogIdx, DogDto dogRequest) throws ResponeException {
        Optional<Dog> dog =  dogRepository.findById(dogIdx);

        try {
            if (dog.isPresent()) {
                dog.get().update(dogRequest.getDogName(), dogRequest.getDogBirth(), dogRequest.getDogType(), dogRequest.getDogSex(), dogRequest.getSurgery());
                dogRepository.save(dog.get());
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
