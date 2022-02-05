package com.example.mummoomserver.domain.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentIdx(Long commentIdx);
    Optional<Comment> deleteByCommentIdx(Long commentIdx);
    List findAllByPost_postIdx(@Param(value = "postIdx") Long postIdx);
}
