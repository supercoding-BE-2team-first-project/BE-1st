package com.github.backend1st.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginDTO {
    private String username;
    private String password;
}
