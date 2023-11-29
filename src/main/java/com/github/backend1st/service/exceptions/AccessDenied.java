package com.github.backend1st.service.exceptions;

public class AccessDenied extends RuntimeException{
    public AccessDenied(String message) {
        super(message);
    }
}
