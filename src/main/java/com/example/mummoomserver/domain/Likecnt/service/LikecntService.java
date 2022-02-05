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
        Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(user).build();

        likecntRepository.delete(likecnt);
    }


    public LikecntDto findByIdx(Long likecntIdx) {
        Likecnt entity = likecntRepository.findById(likecntIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx="+ likecntIdx));
        return new LikecntDto(entity);
    }

}