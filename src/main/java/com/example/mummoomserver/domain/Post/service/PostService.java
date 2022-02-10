package com.example.mummoomserver.domain.Post.service;

import com.example.mummoomserver.config.resTemplate.ResponeException;
import com.example.mummoomserver.domain.Comment.Comment;
import com.example.mummoomserver.domain.Comment.CommentRepository;
import com.example.mummoomserver.domain.Comment.dto.CommentResponseDto;
import com.example.mummoomserver.domain.Likecnt.entity.Likecnt;
import com.example.mummoomserver.domain.Likecnt.repository.LikecntRepository;
import com.example.mummoomserver.domain.NestedComment.NestedComment;
import com.example.mummoomserver.domain.NestedComment.NestedCommentRepository;
import com.example.mummoomserver.domain.NestedComment.dto.NestedCommentResponseDto;
import com.example.mummoomserver.domain.Post.Post;
import com.example.mummoomserver.domain.Post.PostRepository;
import com.example.mummoomserver.domain.Post.dto.PostIdxResponseDto;
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

import static com.example.mummoomserver.config.resTemplate.ResponseTemplateStatus.*;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final NestedCommentRepository nestedCommentRepository;
    private final LikecntRepository likecntRepository;

    public Long save(String email, PostSaveRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        try {
            Post post = Post.builder()
                    .title(requestDto.getTitle())
                    .user(user)
                    .content(requestDto.getContent())
                    .imgUrl(requestDto.getImgUrl())
                    .build();

            return postRepository.save(post).getPostIdx();
        } catch(Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public Long update(Long postIdx, String email, PostUpdateRequestDto requestDto) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponeException(INVALID_USER));
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
        if(post.getUser().getUserIdx() != user.getUserIdx())
            throw new ResponeException(PERMISSION_DENIED);
        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getImgUrl());
        return postIdx;
    }

    public PostIdxResponseDto findByPostIdx(Long postIdx) throws ResponeException {
        Post post = postRepository.findByPostIdx(postIdx)
                .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
        List<Comment> comments = commentRepository.findAllByPost_postIdx(post.getPostIdx());
        PostIdxResponseDto postResIdxDto = new PostIdxResponseDto(post);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        int likecnt = likecntRepository.countByPost_postIdx(postIdx);
        postResIdxDto.setLikecnt(Integer.toString(likecnt));

        for (int i = 0; i<comments.size(); i++){
            CommentResponseDto comment = new CommentResponseDto(comments.get(i));
            commentResponseDtos.add(comment);
            List<NestedComment> nestedComments = nestedCommentRepository.findAllByComment_commentIdx(comments.get(i).getCommentIdx());
            List<NestedCommentResponseDto> nestedCommentResponseDtos = new ArrayList<>();
            for (int j =0; j<nestedComments.size();j++){
                NestedCommentResponseDto nestedCommentResponseDto = new NestedCommentResponseDto(nestedComments.get(j));
                nestedCommentResponseDtos.add(nestedCommentResponseDto);
            }
            comment.setNestedComments(nestedCommentResponseDtos);
        }
        postResIdxDto.setComments(commentResponseDtos);

        return postResIdxDto;
    }

    public List<PostResponseDto> getPosts() throws ResponeException {
        List<Post> entity = postRepository.findAll();
        List<PostResponseDto> postResDtos = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++){
            PostResponseDto postResponseDto = new PostResponseDto(entity.get(i));
            int likecnt = likecntRepository.countByPost_postIdx(entity.get(i).getPostIdx());
            postResponseDto.setLikecnt(Integer.toString(likecnt));
            postResDtos.add(postResponseDto);
        }
        try {
            return postResDtos;
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public void delete(String email, Long postIdx) throws ResponeException {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResponeException(INVALID_USER));
        Post post = postRepository.findByPostIdx(postIdx)
                        .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
        if(post.getUser().getUserIdx() != user.getUserIdx())
            throw new ResponeException(PERMISSION_DENIED);
        postRepository.deleteByPostIdx(postIdx)
                .orElseThrow(() -> new ResponeException(INVALID_POST_IDX));
    }

    public List<PostResponseDto> findMyPost(String email) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResponeException(INVALID_USER));
        List<Post> entity = user.getPosts();
        List<PostResponseDto> postResDtos = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++){
            PostResponseDto postResponseDto = new PostResponseDto(entity.get(i));
            postResDtos.add(postResponseDto);
        }
        try {
            return postResDtos;
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }

    public List<PostResponseDto> findMyLikes(String email) throws ResponeException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResponeException(INVALID_USER));
        List<Likecnt> likes = user.getLikecnts();
        List<Post> entity = new ArrayList<>();
        for(int i = 0; i < likes.size(); i++){
            entity.add(likes.get(i).getPost());
        }
        List<PostResponseDto> postResDtos = new ArrayList<>();
        for (int i = 0; i < entity.size(); i++){
            PostResponseDto postResponseDto = new PostResponseDto(entity.get(i));
            postResDtos.add(postResponseDto);
        }
        try {
            return postResDtos;
        } catch (Exception e){
            throw new ResponeException(DATABASE_ERROR);
        }
    }
}
