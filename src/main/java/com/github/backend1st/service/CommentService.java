package com.github.backend1st.service;

import com.github.backend1st.repository.comments.CommentEntity;
import com.github.backend1st.repository.comments.CommentJpaRepository;
import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.CommentMapper;
import com.github.backend1st.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentJpaRepository commentJpaRepository;

    public List<CommentDto> searchAllComments(Pageable pageable) {
        Page<CommentEntity> commentEntities = commentJpaRepository.findAll(pageable);

        if(commentEntities.isEmpty()) {
            throw new NotFoundException("No Comments!!");
        }

        return commentEntities.stream().map(CommentMapper.INSTANCE::commentEntityToCommentDto).collect(Collectors.toList());
    }

    public CommentDto searchCommentByPostId(Integer postId) {
        return null;
    }

    public CommentDto searchSingleCommentById(Integer commentId) {
        return null;
    }

    public CommentDto registerComment(CustomUserDetails customUserDetails, CommentDto commentDto) {
        return null;
    }

    public CommentDto updateComment(CustomUserDetails customUserDetails, Integer commentId, CommentDto commentDto) {
        return null;
    }

    public CommentDto deleteComment(CustomUserDetails customUserDetails, Integer commentId) {
        return null;
    }
}
