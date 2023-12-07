package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.SignUpLoginService;
import com.github.backend1st.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SignUpLoginController {
    private final SignUpLoginService signUpLoginService;

    @GetMapping("/all")
    public List<UserDTO> allUsers(){
        return signUpLoginService.getAllUsers();
    }


//    @PostMapping("/sign-up")
//    public String register(@RequestBody SignUp signUpRequest){
//        UserDTO createdAccount = signUpLoginService.signUp(signUpRequest);
//        boolean isSuccess = createdAccount!=null;
//        return isSuccess?"회원가입에 성공 하였습니다."+"\n" +
//                "이메일 : "+createdAccount.getEmail()+"\n" +
//                "가입 날짜 : "+createdAccount.getCreatedDate()
//                :"\""+signUpRequest.getEmail()+"\"은 이미 존재하는 이메일 입니다.";
//    }
    @PostMapping("/signup")
    public UserDTO register(@RequestBody SignUp signUpRequest){
        UserDTO createdAccount = signUpLoginService.signUp(signUpRequest);
        boolean isSuccess = createdAccount!=null;
        return isSuccess?createdAccount
                :null;
    }
    @GetMapping("/check-duplicate")
    public DuplicateResponse checkEmail(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email ){
        return signUpLoginService.checkEmail(email, username);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginDTO loginRequest, HttpServletResponse servletResponse){
        LoginResponse loginResponse = signUpLoginService.login(loginRequest);
        servletResponse.setHeader("TOKEN", loginResponse.getToken());
        return loginResponse;
    }

    @GetMapping("/logout-success")
    public String logout(){
        return "로그아웃이 성공적으로 완료되었습니다.";
    }

}
