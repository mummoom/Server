package com.example.mummoomserver.domain.Likecnt.repository;

import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikecntRepository extends JpaRepository<Likecnt,Long> {

    boolean existsByUser_userIdxAndPost_postIdx(long userIdx, long postIdx);

    void deleteByUser_userIdxAndPost_postIdx(long userIdx, long postIdx);
}