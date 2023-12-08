package com.github.backend1st.service;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.posts.PostJpaRepository;
import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpaRepository;
import com.github.backend1st.service.exceptions.ForbiddenException;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.PostMapper;
import com.github.backend1st.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostJpaRepository postJpaRepository;

    private final UsersJpaRepository usersJpaRepository;


    public List<PostDTO> findAllPosts(Pageable pageable) {
        Page<PostEntity> postEntities = postJpaRepository.findAll(pageable);

        if (postEntities.isEmpty()) {
            throw new NotFoundException("게시물을 찾을 수 없습니다.");
        }

        return postEntities.map(PostMapper.INSTANCE::postEntityToPost).getContent();
    }


    public List<PostDTO> findAllPostByUserId(Pageable pageable, CustomUserDetails customUserDetails) {
        UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("userId: " + customUserDetails.getUserId() + " 일치하는 유저정보를 찾을 수 없습니다."));

        List<PostEntity> postEntities = postJpaRepository.findAll(pageable).stream()
                .filter(postEntity -> {
                    UserEntity postUserEntity = postEntity.getUserEntity();
                    return postUserEntity != null && postUserEntity.equals(userEntity);
                })
                .collect(Collectors.toList());

        if (postEntities.isEmpty()) {
            throw new NotFoundException("해당 유저의 게시물을 찾을 수 없습니다.");
        }

        return postEntities.stream().map(PostMapper.INSTANCE::postEntityToPost).collect(Collectors.toList());
    }

    public PostDTO findPostById(Integer post_id) {
        PostEntity postEntity = postJpaRepository.findById(post_id).orElseThrow(() -> new NotFoundException("존재하지 않는 post_id: " + post_id));
        return PostMapper.INSTANCE.postEntityToPost(postEntity);
    }

    @Transactional(transactionManager = "tmJpa")
    public PostDTO registerPost(CustomUserDetails customUserDetails, PostDTO postDTO) {
        log.info("게시물 등록 서비스: {}", postDTO);
        // 현재 로그인한 사용자의 정보를 가져옴
        UserEntity currentUser = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("현재 사용자 정보를 찾을 수 없습니다."));

        // PostDTO에 현재 사용자의 정보를 설정
        postDTO.setUserId(currentUser.getUserId());

        PostEntity postEntity = PostMapper.INSTANCE.postDTOToPostEntity(postDTO);
        PostEntity save = postJpaRepository.save(postEntity);

        PostDTO savePost = PostMapper.INSTANCE.postEntityToPost(save);
        return savePost;
    }

    @Transactional(transactionManager = "tmJpa")
    public PostDTO updatePost(CustomUserDetails customUserDetails, Integer post_id, PostDTO postDTO) {
        log.info("게시물 수정 서비스 postId:{}, {}", post_id, postDTO);

        UserEntity currentUser = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("현재 사용자 정보를 찾을 수 없습니다."));
        postDTO.setUserId(currentUser.getUserId());
        PostEntity existPost = postJpaRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다. : postId " + post_id));

        if (!existPost.getUserEntity().equals(currentUser)) {
            throw new ForbiddenException("수정 권한이 없습니다.");
        }

        // 게시물 업데이트
        existPost.setTitle(postDTO.getTitle());
        existPost.setContent(postDTO.getContent());

        PostEntity save = postJpaRepository.save(existPost);
        return PostMapper.INSTANCE.postEntityToPost(save);
    }

    @Transactional(transactionManager = "tmJpa")
    public void deletePost(CustomUserDetails customUserDetails, Integer post_id) {
        log.info("게시물 삭제 서비스 postId: {} ", post_id);
        UserEntity currentUser = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("현재 사용자 정보를 찾을 수 없습니다."));
        PostEntity existPost = postJpaRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다. : postId " + post_id));
        // 현재 로그인한 사용자와 게시물 작성자를 비교
        if (!existPost.getUserEntity().equals(currentUser)) {
            throw new ForbiddenException("삭제 권한이 없습니다.");
        }
        postJpaRepository.delete(existPost);
    }



}
