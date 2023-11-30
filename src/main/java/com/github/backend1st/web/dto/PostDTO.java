package com.github.backend1st.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {
    private Integer postId;
    private String title;
    private String content;
    private String createAt;
    private Integer favoriteCount;
    private Integer commentCount;
    private Integer userId;
}
