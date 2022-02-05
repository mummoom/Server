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
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Comment comment = commentRepository.findByCommentIdx(commentIdx)
                .orElseThrow(() ->new IllegalArgumentException("없는 댓글 입니다."));

        NestedComment nestedComment = NestedComment.builder()
                .content(requestDto.getContent())
                .user(user)
                .comment(comment)
                .build();

        return nestedCommentRepository.save(nestedComment).getNestedCommentIdx();
    }

    public void delete(String email, Long nestedCommentIdx) throws ResponeException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        NestedComment nestedComment = nestedCommentRepository.findByNestedCommentIdx(nestedCommentIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 대댓글입니다."));
        if(nestedComment.getUser().getUserIdx() != user.getUserIdx())
            throw new IllegalArgumentException("대댓글작성자만 삭제할 수 있습니다.");
        nestedCommentRepository.deleteByNestedCommentIdx(nestedCommentIdx);
    }
}
