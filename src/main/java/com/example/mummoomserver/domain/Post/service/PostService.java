package com.example.mummoomserver.domain.Post.service;

import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        return postRepository.save(requestDto.toEntity()).getPostIdx();
    }

    @Transactional
    public Long update(Long postIdx, PostUpdateRequestDto requestDto){
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx=" + postIdx));
        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl(), requestDto.getStatus());
        return postIdx;
    }
    public PostResponseDto findByPostIdx(Long postIdx){
        Post entity = postRepository.findById(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx="+ postIdx));
        return new PostResponseDto(entity);
    }
}
