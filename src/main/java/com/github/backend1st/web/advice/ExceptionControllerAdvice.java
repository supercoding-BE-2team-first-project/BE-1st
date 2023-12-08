package com.github.backend1st.web.advice;

import com.github.backend1st.service.exceptions.AccessDenied;
import com.github.backend1st.service.exceptions.ForbiddenException;
import com.github.backend1st.service.exceptions.NotAcceptException;
import com.github.backend1st.service.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException n) {
        return n.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)//전달될 코드
    @ExceptionHandler(NotAcceptException.class)//해당 Exception 발생시
    public String handleNotAcceptException(NotAcceptException n) {
        return n.getMessage();//프론트에 전달될 메세지
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)//전달될 코드
    @ExceptionHandler(AccessDenied.class)//해당 Exception 발생시
    public String handleNotAccessDenied(AccessDenied n) {
        return n.getMessage();//프론트에 전달될 메세지
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbiddenException(ForbiddenException forbiddenException) {
        return forbiddenException.getMessage();
    }
}
