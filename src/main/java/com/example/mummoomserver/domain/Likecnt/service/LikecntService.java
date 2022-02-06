package com.example.mummoomserver.domain.Likecnt.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.repository.LikecntRepository;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikecntService {
    private final LikecntRepository likecntRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;



    public boolean postLike(String email, long postIdx) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("회원정보를 찾을 수 없습니다."));
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));
        boolean check = likecntRepository.existsByUser_userIdxAndPost_postIdx(user.getUserIdx(),postIdx);
        if (check == false){
            Likecnt likecnt = Likecnt.builder()
                    .user(user)
                    .post(post)
                    .status("ACTIVE")
                    .build();
            likecntRepository.save(likecnt);
            return true;
        }else{
            likecntRepository.deleteByUser_userIdxAndPost_postIdx(user.getUserIdx(), postIdx);
            return false;
        }
    }

}