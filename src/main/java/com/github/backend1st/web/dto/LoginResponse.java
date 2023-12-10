package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    @ApiModelProperty(name="message", value = "메세지", example = "로그인에 성공하였습니다.")
    private String message;
    @ApiModelProperty(name="userId", value = "아이디", example = "1")
    private Integer userId;
    @ApiModelProperty(name="username", value = "이름", example = "zlatan")
    private String username;
    @ApiModelProperty(name="token", value = "토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmM1QG5hdmVyLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfQURNSU4iXSwiZXhwIjoxNzAxODkyMjY4fQ.i_mOSmreOJYqV5f9tq0J89f4-aBRjfs1vVy6ZH9NiKM")
    private String token;
}
