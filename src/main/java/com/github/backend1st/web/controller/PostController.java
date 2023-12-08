package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.PostService;
import com.github.backend1st.web.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<PostDTO> findAllPost(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        log.info("get요청");
        List<PostDTO> postDTOS = postService.findAllPost(customUserDetails);
        log.info("get 성공");
        return postDTOS;
    }

    @GetMapping("/posts/{post_id}")
    public PostDTO findPostById(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer post_id) {
        log.info("특정 게시물 조회 요청: {}", post_id);
        PostDTO postDTO = postService.findPostById(post_id);
        log.info("특정 게시물 조회 성공: {}", post_id);
        return postDTO;
    }

    @PostMapping("/posts")
    public PostDTO register(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody PostDTO postDTO) {
        log.info("게시물 등록 요청: {}", postDTO);
        PostDTO registeredPost = postService.registerPost(customUserDetails, postDTO);
        log.info("게시물 등록 성공: {}", registeredPost);
        return registeredPost;
    }

    @PutMapping("/posts/{post_id}")
    public PostDTO update(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer post_id, @RequestBody PostDTO postDTO) {
        log.info("게시물 수정 요청 postId:{}, {}", post_id, postDTO);
        PostDTO updatedPost = postService.updatePost(post_id, postDTO);
        log.info("게시물 수정 성공: {} ", updatedPost);
        return updatedPost;
    }

    @DeleteMapping("/posts/{post_id}")
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Integer post_id) {
        log.info("게시물 수정 요청 postId:{}", post_id);
        postService.deletePost(post_id);
        log.info("게시물 삭제 성공 postId:{}", post_id);
    }
}
