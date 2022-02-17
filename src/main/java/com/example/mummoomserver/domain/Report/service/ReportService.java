package com.example.mummoomserver.domain.Report.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.domain.Report.Report;
import com.example.mummoomserver.domain.Report.ReportRepository;
import com.example.mummoomserver.domain.Report.dto.ReportSaveRequestDto;
import com.example.mummoomserver.login.users.User;
import com.example.mummoomserver.login.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ReportService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    public Long save(String email, Long postIdx, ReportSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
        try {
            Report report = Report.builder()
                    .reason(requestDto.getReason())
                    .user(user)
                    .post(post)
                    .build();
            return reportRepository.save(report).getReportIdx();
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public Long saveComment(String email, Long commentIdx, ReportSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        Comment comment = commentRepository.findByCommentIdx(commentIdx)
                .orElseThrow(() -> new ResponeException(INVALID_COMMENT_IDX));
        try {
            Report report = Report.builder()
                    .reason(requestDto.getReason())
                    .user(user)
                    .comment(comment)
                    .build();
            return reportRepository.save(report).getReportIdx();
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }
}
