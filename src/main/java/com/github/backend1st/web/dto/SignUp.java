package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUp {
    @ApiModelProperty(name="email", value = "이메일", example = "abc@naver.com")
    private String email;
    @ApiModelProperty(name="password", value = "비밀번호", example = "abcd1234")
    private String password;
    @ApiModelProperty(name="username", value = "이름", example = "홍길동")
    private String username;
}
