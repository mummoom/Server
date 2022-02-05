package com.example.mummoomserver.domain.NestedComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NestedCommentRepository extends JpaRepository<NestedComment, Long> {
    Optional<NestedComment> findByNestedCommentIdx(Long nestedCommentIdx);
    Optional<NestedComment> deleteByNestedCommentIdx(Long nestedCommentIdx);
    List findAllByComment_commentIdx(@Param(value = "commentIdx") Long commentIdx);
}
