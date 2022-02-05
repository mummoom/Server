package com.example.mummoomserver.domain.Likecnt.repository;


import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.login.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikecntRepository extends JpaRepository<Likecnt, Long> {
    Long findByPostIdx(Long postIdx);
    Optional<Likecnt> findByUser_IdxAndPost_Idx(@Param(value = "userIdx") Long userIdx, @Param(value = "postIdx") Long postIdx) ;

}