package com.github.backend1st.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString
public class User {
    private Integer userId;
    private String UserName;
    private String email;
    private String password;
    private String regDate;
}
