package com.github.backend1st.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class DuplicateResponse {
    @ApiModelProperty(name="isDuplicate", value = "중복 여부", example = "true")
    private boolean isDuplicate;
    @ApiModelProperty(name="message", value = "메세지", example = "사용자명 또는 이메일이 이미 사용 중입니다.")
    private String message;
}
