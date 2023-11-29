/*
package com.github.backend1st.service;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.posts.PostJpaRepository;
import com.github.backend1st.repository.users.UserJpaRepository;
import com.github.backend1st.service.mapper.PostMapper;
import com.github.backend1st.web.dto.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    @Autowired
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public List<Post> findAllPost() {
        List<PostEntity> postEntities = postJpaRepository.findAll();
        if (postEntities.isEmpty()) throw new NullPointerException("posts를 찾을 수 없습니다.");
        return postEntities.stream().map(PostMapper.INSTANCE::postEntityToPost).collect(Collectors.toList());
    }
}
*/
