package com.example.mummoomserver.domain.Comment.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long save(Long postIdx, String email, CommentSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
        try {
            Comment comment = Comment.builder()
                    .content(requestDto.getContent())
                    .user(user)
                    .post(post)
                    .build();

            return commentRepository.save(comment).getCommentIdx();
        } catch(Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public void delete(String email, Long commentIdx) throws ResponeException {
        Comment comment = commentRepository.findByCommentIdx(commentIdx)
                        .orElseThrow(() -> new ResponeException(INVALID_COMMENT_IDX));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        if (comment.getUser().getUserIdx() != user.getUserIdx())
            throw new ResponeException(PERMISSION_DENIED);
        commentRepository.deleteByCommentIdx(commentIdx)
                .orElseThrow(() -> new ResponeException(INVALID_COMMENT_IDX));
    }
}
