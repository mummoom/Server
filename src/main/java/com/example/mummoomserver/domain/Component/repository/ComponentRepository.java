package com.example.mummoomserver.domain.Component.repository;


import com.example.mummoomserver.domain.Component.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentRepository  extends JpaRepository<Component,Long> {


}
