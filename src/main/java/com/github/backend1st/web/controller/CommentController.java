package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.CommentService;
import com.github.backend1st.web.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public List<CommentDto> searchAllComments(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        log.info("--> GET /comments request");
        Pageable pageable = PageRequest.of(page, size);
        List<CommentDto> commentList = commentService.searchAllComments(pageable);
        log.info("<-- GET /comments response: " + commentList);

        return commentList;
    }

    @GetMapping("/comments/{post_id}")
    public List<CommentDto> searchCommentByPostId(@PathVariable String post_id) {
        log.info("--> GET /comments/{ post_id: " + post_id + " } request" );
        List<CommentDto> commentListByPostId = commentService.searchCommentByPostId(post_id);
        log.info("<-- GET /comments/{ post_id: " + post_id + " } response: " + commentListByPostId);

        return commentListByPostId;
    }

    @GetMapping("/comments/single/{comment_id}")
    public CommentDto searchSingleCommentById(@PathVariable String comment_id) {
        log.info("--> GET /comments/{ comment_id: " + comment_id + " } request");
        CommentDto commentList = commentService.searchSingleCommentById(comment_id);
        log.info("<-- GET /comments/{ comment_id: " + comment_id + " } response: " + commentList);

        return commentList;
    }

    @PostMapping("/comments")
    public CommentDto registerComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CommentDto commentDto) {
        log.info("--> POST /comments request");
        CommentDto registerComment = commentService.registerComment(customUserDetails, commentDto);
        log.info("<-- POST /comments response: " + registerComment);

        return registerComment;
    }

    @PutMapping("/comments/{comment_id}")
    public CommentDto modifyComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String comment_id, @RequestBody CommentDto commentDto) {
        return commentService.updateComment(customUserDetails, comment_id, commentDto);
    }

    @DeleteMapping("/comments/{comment_id}")
    public CommentDto deleteComment(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable String comment_id) {
        return commentService.deleteComment(customUserDetails, comment_id);
    }
}
