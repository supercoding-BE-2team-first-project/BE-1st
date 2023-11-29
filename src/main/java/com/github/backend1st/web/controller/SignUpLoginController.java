package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.SignUpLoginService;
import com.github.backend1st.web.dto.LoginDTO;
import com.github.backend1st.web.dto.SignUp;
import com.github.backend1st.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class SignUpLoginController {
    private final SignUpLoginService signUpLoginService;

    @GetMapping("/all")
    public List<UserDTO> allUsers(){
        return signUpLoginService.getAllUsers();
    }


    @PostMapping("/sign-up")
    public String register(@RequestBody SignUp signUpRequest){
        UserDTO createdAccount = signUpLoginService.signUp(signUpRequest);
        boolean isSuccess = createdAccount!=null;
        return isSuccess?"회원가입에 성공 하였습니다."+"\n" +
                "이메일 : "+createdAccount.getEmail()+"\n" +
                "가입 날짜 : "+createdAccount.getCreatedDate()
                :"\""+signUpRequest.getEmail()+"\"은 이미 존재하는 이메일 입니다.";
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginRequest, HttpServletResponse servletResponse){
        String token = signUpLoginService.login(loginRequest);
        servletResponse.setHeader("TOKEN", token);
        return "로그인에 성공 하였습니다.\n"+"로그인 이메일 : "+loginRequest.getEmail();
    }

    @GetMapping("/logout-success")
    public String logout(){
        return "로그아웃 완료";
    }

}
