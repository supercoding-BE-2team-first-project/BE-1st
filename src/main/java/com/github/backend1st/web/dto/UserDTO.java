package com.github.backend1st.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String email;
    private LocalDateTime regDate;
    private String createdDate;

}
