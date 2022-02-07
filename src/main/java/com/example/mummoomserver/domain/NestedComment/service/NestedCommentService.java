package com.example.mummoomserver.domain.NestedComment.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.NestedComment.NestedComment;
import com.example.mummoomserver.domain.NestedComment.NestedCommentRepository;
import com.example.mummoomserver.domain.NestedComment.dto.NestedCommentSaveDto;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;


@RequiredArgsConstructor
@Service
@Transactional
public class NestedCommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NestedCommentRepository nestedCommentRepository;

    public Long save (String email, Long commentIdx, NestedCommentSaveDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        Comment comment = commentRepository.findByCommentIdx(commentIdx)
                .orElseThrow(() ->new ResponeException(INVALID_COMMENT_IDX));
        try {
            NestedComment nestedComment = NestedComment.builder()
                    .content(requestDto.getContent())
                    .user(user)
                    .comment(comment)
                    .build();

            return nestedCommentRepository.save(nestedComment).getNestedCommentIdx();
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public void delete(String email, Long nestedCommentIdx) throws ResponeException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        NestedComment nestedComment = nestedCommentRepository.findByNestedCommentIdx(nestedCommentIdx)
                .orElseThrow(() -> new ResponeException(INVALID_NESTED_IDX));
        if(nestedComment.getUser().getUserIdx() != user.getUserIdx())
            throw new ResponeException(PERMISSION_DENIED);
        nestedCommentRepository.deleteByNestedCommentIdx(nestedCommentIdx);
    }
}
