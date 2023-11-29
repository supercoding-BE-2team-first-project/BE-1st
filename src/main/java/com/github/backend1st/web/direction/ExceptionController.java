package com.github.backend1st.web.direction;

import com.github.backend1st.service.exceptions.AccessDenied;
import com.github.backend1st.service.exceptions.NotAcceptException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exceptions")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public void entrypointException() {
        throw new NotAcceptException( "인증 과정에서 에러");
    }

    @GetMapping(value = "/access-denied")
    public void accessDeniedException() {
        throw new AccessDenied("권한이 없습니다.");
    }
}