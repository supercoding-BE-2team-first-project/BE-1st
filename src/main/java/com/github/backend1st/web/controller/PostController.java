//package com.github.backend1st.web.controller;
//
//import com.github.backend1st.service.PostService;
//import com.github.backend1st.web.dto.Post;
//import com.github.backend1st.web.dto.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Slf4j
//public class PostController {
//
//    private final PostService postService;
//
//    @GetMapping("/posts")
//    public List<Post> findAllPost() {
//        log.info("get요청");
//        List<Post> posts = postService.findAllPost();
//        log.info("get 성공");
//        return posts;
//    }
//}
