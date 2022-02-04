package com.example.mummoomserver.domain.Comment.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(Long postIdx, Long userIdx, CommentSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByUserIdx(userIdx)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));

        requestDto.setUserIdx(user);
        requestDto.setPostIdx(post);
        return commentRepository.save(requestDto.toEntity()).getCommentIdx();
    }

    public void delete(Long commentIdx) throws ResponeException {
        commentRepository.deleteByCommentIdx(commentIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다. commentIdx="+commentIdx));
    }
}
