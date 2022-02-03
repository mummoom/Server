package com.example.mummoomserver.domain.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostIdx(Long postIdx);
    Optional<Post> deleteByPostIdx(Long postIdx);
}
