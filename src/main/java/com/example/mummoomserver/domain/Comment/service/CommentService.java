package com.example.mummoomserver.domain.Comment.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.Comment.dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(CommentSaveRequestDto requestDto) throws ResponeException {
        return commentRepository.save(requestDto.toEntity()).getCommentIdx();
    }

}
