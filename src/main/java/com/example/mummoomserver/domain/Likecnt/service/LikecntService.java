package com.example.mummoomserver.domain.Likecnt.service;

import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.repository.LikecntRepository;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikecntService {
    private final LikecntRepository likecntRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;



    public void like(Long postIdx,String userEmail){

        Post post =  postRepository.findById(postIdx).get();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(user).build();

        likecntRepository.save(likecnt);


    }

    public void unlike(Long postIdx,String userEmail){

        Post post =  postRepository.findById(postIdx).get();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Likecnt likecntIdx = likecntRepository.findByUser_IdxAndPost_Idx(user.getUserIdx(),post.getPostIdx()).get();

//               .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
//
//        Likecnt likecnt1 = likecntRepository.findLikecntByPost(postIdx)
//                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        likecntRepository.delete(likecntIdx);
    }

}