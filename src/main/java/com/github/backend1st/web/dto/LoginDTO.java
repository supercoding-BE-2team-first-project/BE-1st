package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginDTO {
    @ApiModelProperty(name="username", value = "이름 또는 이메일", example = "abc@naver.com")
    private String username;

    @ApiModelProperty(name="password", value = "비밀번호", example = "abcd1234")
    private String password;
}
