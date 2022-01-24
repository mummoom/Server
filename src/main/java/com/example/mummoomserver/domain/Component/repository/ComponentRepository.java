package com.example.mummoomserver.domain.Component.repository;


import com.example.mummoomserver.domain.Component.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComponentRepository  extends JpaRepository<Component,Long> {

}
