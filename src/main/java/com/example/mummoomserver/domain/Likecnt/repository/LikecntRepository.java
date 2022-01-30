package com.example.mummoomserver.domain.Likecnt.repository;


import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikecntRepository extends JpaRepository<Likecnt, Long> {
}
