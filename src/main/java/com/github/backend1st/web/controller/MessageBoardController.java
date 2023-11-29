package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.MessageBoardService;
import com.github.backend1st.service.SignUpLoginService;
import com.github.backend1st.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class MessageBoardController {
    private final MessageBoardService messageBoardService;
    private final SignUpLoginService signUpLoginService;
    @GetMapping("/all")
    public List<UserDTO> allUsersByAdmin(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return signUpLoginService.getAllUsersByAdmin(customUserDetails);
    }
}
