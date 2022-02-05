package com.example.mummoomserver.domain.Comment.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
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

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long save(Long postIdx, String email, CommentSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .user(user)
                .post(post)
                .build();

        return commentRepository.save(comment).getCommentIdx();
    }

    public void delete(String email, Long commentIdx) throws ResponeException {
        Comment comment = commentRepository.findByCommentIdx(commentIdx)
                        .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));
        if (comment.getUser().getUserIdx() != user.getUserIdx())
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        commentRepository.deleteByCommentIdx(commentIdx)
                .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다. commentIdx="+commentIdx));
    }
}
