package com.github.backend1st.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentPostDto {

    private String message;
    private String commentId;
    private String userId;
    private String postId;
    private String content;
    private String createdAt;

}
