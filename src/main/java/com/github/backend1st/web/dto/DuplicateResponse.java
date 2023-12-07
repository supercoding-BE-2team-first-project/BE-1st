package com.github.backend1st.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DuplicateResponse {
    private boolean isDuplicate;
    private String message;
}
