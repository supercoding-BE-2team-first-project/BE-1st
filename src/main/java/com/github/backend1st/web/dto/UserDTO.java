package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    @ApiModelProperty(name="message", value = "메세지", example = "")
    private String message;
    @ApiModelProperty(name="userId", value = "아이디", example = "1")
    private String userId;
    @ApiModelProperty(name="username", value = "이름", example = "홍길동")
    private String username;
    @ApiModelProperty(name="email", value = "이메일", example = "abc@naver.com")
    private String email;

}
