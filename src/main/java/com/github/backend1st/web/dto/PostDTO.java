package com.github.backend1st.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.backend1st.repository.users.UserEntity;
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
