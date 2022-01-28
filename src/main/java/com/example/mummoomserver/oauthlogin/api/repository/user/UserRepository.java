package com.example.mummoomserver.oauthlogin.api.repository.user;

import com.example.mummoomserver.oauthlogin.api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
