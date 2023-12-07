package com.github.backend1st.web.controller;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.service.SignUpLoginService;
import com.github.backend1st.web.dto.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation("모든 유저 정보 조회 (ADMIN 권한 필요)")
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
    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public UserDTO register(
            @ApiParam(name = "signUpRequest", value = "회원가입 요청값")
            @RequestBody SignUp signUpRequest){
        UserDTO createdAccount = signUpLoginService.signUp(signUpRequest);
        boolean isSuccess = createdAccount!=null;
        return isSuccess?createdAccount
                :null;
    }
    @ApiOperation("중복 이메일 조회")
    @GetMapping("/check-duplicate")
    public DuplicateResponse checkEmail(
            @ApiParam(name = "username", value = "이름", example = "즐라탄 이브라히모비치")
            @RequestParam(name = "username") String username,
            @ApiParam(name = "email", value = "이메일", example = "zlatan@naver.com")
            @RequestParam(name = "email") String email ){
        return signUpLoginService.checkEmail(email, username);
    }
    @ApiOperation("이메일 또는 이름으로 로그인")
    @PostMapping("/login")
    public LoginResponse login(
            @ApiParam(name = "loginRequest", value = "로그인 요청값")
            @RequestBody LoginDTO loginRequest, HttpServletResponse servletResponse){
        LoginResponse loginResponse = signUpLoginService.login(loginRequest);
        servletResponse.setHeader("TOKEN", loginResponse.getToken());
        return loginResponse;
    }

    @ApiOperation("/logout 으로 접속해 로그아웃 성공시 리다이렉션 되는 api")
    @GetMapping("/logout-success")
    public String logout(){
        return "로그아웃이 성공적으로 완료되었습니다.";
    }

}
