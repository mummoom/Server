package com.example.mummoomserver.domain.Dog.repository;

import com.example.mummoomserver.domain.Dog.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

}
