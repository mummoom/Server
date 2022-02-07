package com.example.mummoomserver.domain.Dog.repository;

import com.example.mummoomserver.domain.Dog.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    List<Dog> findDogsByUser_userIdxAndStatus(Long userIdx, String status);
}
