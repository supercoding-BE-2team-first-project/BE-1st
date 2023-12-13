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
import com.github.backend1st.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<CommentDto> searchCommentByPostId(String postId) {
        List<CommentEntity> commentEntities = commentJpaRepository.findAllByPostId(postId   );
        System.out.println(commentEntities);

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
    public CommentDto registerComment(CustomUserDetails customUserDetails, CommentDto commentDto) {
        // UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
        UserEntity userEntity = usersJpaRepository.findById(Integer.parseInt(commentDto.getUserId()))
                .orElseThrow(() -> new NotFoundException("일치하는 userId(" + commentDto.getUserId() + ")를 찾을 수 없습니다."));
        PostEntity postEntity = postJpaRepository.findById(Integer.parseInt(commentDto.getPostId()))
                .orElseThrow(() -> new NotFoundException("일치하는 postId(" + commentDto.getPostId() + ")를 찾을 수 없습니다."));

        commentDto.setUserId(String.valueOf(userEntity.getUserId()));

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setUserId(commentDto.getUserId());
        commentEntity.setPostId(commentDto.getPostId());
        commentEntity.setCreatedAt(LocalDateTime.now());

        CommentEntity saveComment = commentJpaRepository.save(commentEntity);

        CommentDto commentPostDto = new CommentDto();
        commentPostDto.setMessage("댓글이 성공적으로 작성되었습니다.");
        commentPostDto.setCommentId(saveComment.getCommentId());
        commentPostDto.setUserId(String.valueOf(saveComment.getUserId()));
        commentPostDto.setPostId(String.valueOf(saveComment.getPostId()));
        commentPostDto.setContent(saveComment.getContent());
        commentPostDto.setCreatedAt(String.valueOf(saveComment.getCreatedAt()));

        return commentPostDto;
    }

    @Transactional(transactionManager = "tmJpa")
    public CommentDto updateComment(CustomUserDetails customUserDetails, String commentId, CommentDto commentDto) {
        Integer commentIdInt = Integer.parseInt(commentId);
        CommentEntity commentEntity = commentJpaRepository.findById(commentIdInt)
                .orElseThrow(() -> new NotFoundException("일치하는 commentId(" + commentIdInt + ")를 찾을 수 없습니다."));

        commentEntity.setCommentId(commentIdInt);
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setUserId(commentDto.getUserId());
        commentEntity.setPostId(commentDto.getPostId());
        commentEntity.setUpdatedAt(LocalDateTime.now());

        CommentDto commentUpdateDto = new CommentDto();
        commentUpdateDto.setMessage("댓글이 성공적으로 수정되었습니다.");
        commentUpdateDto.setCommentId(commentEntity.getCommentId());
        commentUpdateDto.setUserId(String.valueOf(commentEntity.getUserId()));
        commentUpdateDto.setPostId(String.valueOf(commentEntity.getPostId()));
        commentUpdateDto.setContent(commentEntity.getContent());
        commentUpdateDto.setUpdatedAt(String.valueOf(commentEntity.getUpdatedAt()));

        return commentUpdateDto;
    }

    public CommentDto deleteComment(CustomUserDetails customUserDetails, String commentId) {
        Integer commentIdInt = Integer.parseInt(commentId);
        CommentEntity commentEntity = commentJpaRepository.findById(commentIdInt)
                .orElseThrow(() -> new NotFoundException("일치하는 commentId(" + commentIdInt + ")를 찾을 수 없습니다."));

        commentJpaRepository.deleteById(commentIdInt);

        CommentDto commentDeleteDto = new CommentDto();
        commentDeleteDto.setMessage("댓글이 성공적으로 삭제되었습니다.");

        return commentDeleteDto;
    }

}
