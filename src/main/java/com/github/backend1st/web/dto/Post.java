package com.github.backend1st.web.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    private Integer postId;
    private String title;
    private String content;
    private String createAt;
    private Integer favoriteCount;
    private Integer commentCount;
    private Integer userId;
}
