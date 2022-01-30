package com.example.mummoomserver.domain.Likecnt.service;

import com.example.mummoomserver.domain.Likecnt.dto.LikecntDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.repository.LikecntRepository;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikecntService {
    private final LikecntRepository likecntRepository;
    private final PostRepository postRepository;



   public void like(Long postIdx){

       Post post =  postRepository.findById(postIdx).get();
       Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(userIdx).build();

       likecntRepository.save(likecnt);


   }

   public void unlike(Long postIdx){

       Post post =  postRepository.findById(postIdx).get();
       Likecnt likecnt = Likecnt.builder().postIdx(post).userIdx(userIdx).build();

       likecntRepository.delete(likecnt);
   }


    public LikecntDto findByIdx(Long likecntIdx) {
        Likecnt entity = likecntRepository.findById(likecntIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx="+ likecntIdx));
        return new LikecntDto(entity);
    }

}
