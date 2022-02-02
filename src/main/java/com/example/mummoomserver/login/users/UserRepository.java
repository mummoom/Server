package com.example.mummoomserver.login.users;

import com.example.mummoomserver.login.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 검사하는 메소드 저장
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserIdx(Long userIdx);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
