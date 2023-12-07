package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDTO {
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private String message;
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private Integer postId;
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private String title;
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private String content;
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private String createAt;
    
    private Integer favoriteCount;
    private Integer commentCount;
    private Integer userId;
}
