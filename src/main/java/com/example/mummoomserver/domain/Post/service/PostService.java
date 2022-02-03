package com.example.mummoomserver.domain.Post.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.domain.Post.dto.PostResponseDto;
import com.example.mummoomserver.domain.Post.dto.PostSaveRequestDto;
import com.example.mummoomserver.domain.Post.dto.PostUpdateRequestDto;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(Long userIdx, PostSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByUserIdx(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        requestDto.setUserIdx(user);
        return postRepository.save(requestDto.toEntity()).getPostIdx();
    }

    @Transactional
    public Long update(Long postIdx, PostUpdateRequestDto requestDto) throws ResponeException {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx=" + postIdx));
        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl(), requestDto.getStatus());
        return postIdx;
    }
    public PostResponseDto findByPostIdx(Long postIdx) throws ResponeException {
        Post entity = postRepository.findById(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. postIdx="+ postIdx));
        return new PostResponseDto(entity);
    }

    public List<PostResponseDto> getPosts() throws ResponeException {
        List<Post> entity = postRepository.findAll();
        List<PostResponseDto> postResDtos = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++){
            PostResponseDto postResponseDto = new PostResponseDto(entity.get(i));
            postResDtos.add(postResponseDto);
        }
        return postResDtos;
    }

    public void delete(Long postIdx) throws ResponeException {
        postRepository.deleteByPostIdx(postIdx);
    }
}
