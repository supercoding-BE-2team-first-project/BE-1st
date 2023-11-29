package com.github.backend1st.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString
public class User {
    private Integer userId;
    private String email;
    private String password;
}
