package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes/{post_id}")
    public String registerLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("post_id") Integer postId) {
        return likeService.registerLike(customUserDetails, postId);
    }

    @DeleteMapping("/likes/{post_id}")
    public String deleteLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("post_id") Integer postId) {
        return likeService.deleteLike(customUserDetails, postId);
    }

    @GetMapping("/likes/{post_id}")
    public boolean checkLike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("post_id") Integer postId) {
        return likeService.checklike(customUserDetails, postId);
    }

}
