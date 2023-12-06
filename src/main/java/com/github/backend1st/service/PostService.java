package com.github.backend1st.service;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.posts.PostJpaRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.PostMapper;
import com.github.backend1st.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostJpaRepository postJpaRepository;
    public List<PostDTO> findAllPost() {
        List<PostEntity> postEntities = postJpaRepository.findAll();
        if (postEntities.isEmpty()) throw new NullPointerException("posts를 찾을 수 없습니다.");
        return postEntities.stream().map(PostMapper.INSTANCE::postEntityToPost).collect(Collectors.toList());
    }

    public PostDTO registerPost(PostDTO postDTO) {
        log.info("게시물 등록 서비스: {}", postDTO);
        PostEntity postEntity = PostMapper.INSTANCE.postDTOToPostEntity(postDTO);
        PostEntity save = postJpaRepository.save(postEntity);
        PostDTO savePost = PostMapper.INSTANCE.postEntityToPost(save);
        return savePost;
    }

    public PostDTO updatePost(Integer post_id, PostDTO postDTO) {
        log.info("게시물 수정 서비스 postId:{}, {}", post_id, postDTO);
        PostEntity existPost = postJpaRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다. : postId " + post_id));

        // 게시물 업데이트
        existPost.setTitle(postDTO.getTitle());
        existPost.setContent(postDTO.getContent());

        PostEntity save = postJpaRepository.save(existPost);
        return PostMapper.INSTANCE.postEntityToPost(save);
    }

    public void deletePost(Integer post_id) {
        log.info("게시물 삭제 서비스 postId: {} ", post_id);
        PostEntity existPost = postJpaRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("게시물이 존재하지 않습니다. : postId " + post_id));
        postJpaRepository.delete(existPost);
    }

    public PostDTO findPostById(Integer post_id) {
        PostEntity postEntity = postJpaRepository.findById(post_id)
                .orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다. : postId " + post_id));
        return PostMapper.INSTANCE.postEntityToPost(postEntity);
    }
}
