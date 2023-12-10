package com.github.backend1st.service;

import com.github.backend1st.repository.comments.CommentEntity;
import com.github.backend1st.repository.comments.CommentJpaRepository;
import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.posts.PostJpaRepository;
import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpaRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.CommentMapper;
import com.github.backend1st.service.mapper.PostMapper;
import com.github.backend1st.web.dto.CommentDto;
import com.github.backend1st.web.dto.CommentPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UsersJpaRepository usersJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    public List<CommentDto> searchAllComments(Pageable pageable) {
        Page<CommentEntity> commentEntities = commentJpaRepository.findAll(pageable);

        if(commentEntities.isEmpty()) {
            throw new NotFoundException("No Comments!!");
        }

        return commentEntities.stream().map(CommentMapper.INSTANCE::commentEntityToCommentDto).collect(Collectors.toList());
    }

    public List<CommentDto> searchCommentById(String postId) {
        Integer idInt = Integer.parseInt(postId);
        List<CommentEntity> commentEntities = commentJpaRepository.findAllById(Collections.singleton(idInt));

        if(commentEntities.isEmpty()) {
            throw new NotFoundException("No Comments!!");
        }

        return commentEntities.stream().map(CommentMapper.INSTANCE::commentEntityToCommentDto).collect(Collectors.toList());

    }

    public CommentDto searchSingleCommentById(String commentId) {
        Integer idInt = Integer.parseInt(commentId);
        CommentEntity commentEntity = commentJpaRepository.findById(idInt).orElseThrow(() -> new NotFoundException("해당 comment_id를 찾을 수 없습니다."));

        return CommentMapper.INSTANCE.commentEntityToCommentDto(commentEntity);
    }

    @Transactional(transactionManager = "tmJpa")
    public CommentPostDto registerComment(CustomUserDetails customUserDetails, CommentDto commentDto) {
        Integer idInt = Integer.parseInt(commentDto.getUserId());

        // UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
        UserEntity userEntity = usersJpaRepository.findById(idInt)
                .orElseThrow(() -> new NotFoundException("현재 사용자 정보를 찾을 수 없습니다."));
        commentDto.setUserId(String.valueOf(userEntity.getUserId()));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentId(commentDto.getCommentId());
        commentEntity.getUserEntity().setUserId(Integer.valueOf(commentDto.getUserId()));
        commentEntity.getPostEntity().setPostId(Integer.valueOf(commentDto.getPostId()));

        CommentEntity saveComment = commentJpaRepository.save(commentEntity);

        CommentPostDto commentPostDto = new CommentPostDto();
        commentPostDto.setMessage("댓글이 성공적으로 작성되었습니다.");
        commentPostDto.setCommentId(String.valueOf(saveComment.getCommentId()));
        commentPostDto.setUserId(String.valueOf(saveComment.getUserEntity().getUserId()));
        commentPostDto.setPostId(String.valueOf(saveComment.getPostEntity().getPostId()));
        commentPostDto.setContent(saveComment.getContent());
        commentPostDto.setCreatedAt(saveComment.getCreateAt());

        return commentPostDto;
    }

    public CommentDto updateComment(CustomUserDetails customUserDetails, Integer commentId, CommentDto commentDto) {
        return null;
    }

    public CommentDto deleteComment(CustomUserDetails customUserDetails, Integer commentId) {
        return null;
    }

}
