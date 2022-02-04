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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikecntService {
    private final LikecntRepository likecntRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;



   public void like(String userNickname,Long postIdx){

       Post post =  postRepository.findById(postIdx).get();
       Optional<User> user = userRepository.findByNickName(userNickname);
       Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(user.get()).build();

       likecntRepository.save(likecnt);


   }

   public void unlike(String userNickname,Long postIdx){

       Post post =  postRepository.findById(postIdx).get();
       Optional<User> user = userRepository.findByNickName(userNickname);
       Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(user.get()).build();

       likecntRepository.delete(likecnt);
   }


    public LikecntDto findByIdx(Long likecntIdx) {
        Likecnt entity = likecntRepository.findById(likecntIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx="+ likecntIdx));
        return new LikecntDto(entity);
    }

}
