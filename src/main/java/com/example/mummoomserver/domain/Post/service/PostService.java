package com.example.mummoomserver.domain.Post.service;

import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
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
}
