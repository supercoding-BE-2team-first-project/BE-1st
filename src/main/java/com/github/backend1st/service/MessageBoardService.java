package com.github.backend1st.service;

import com.github.backend1st.config.security.JwtTokenProvider;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpa;
import com.github.backend1st.service.exceptions.NotAcceptException;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.UserMapper;
import com.github.backend1st.web.dto.LoginDTO;
import com.github.backend1st.web.dto.SignUp;
import com.github.backend1st.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageBoardService {
    private final UsersJpa usersJpa;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntityList = usersJpa.findAll();
        return userEntityList.stream().map(UserMapper.INSTANCE::userEntityToUserDTO).collect(Collectors.toList());
    };
    @Transactional(transactionManager = "tmJpa")
    public UserDTO signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        if(usersJpa.existsByEmail(email))return null;//이메일 같은게 존재할시 실패

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))//암호화
                .regDate(LocalDateTime.now())
                .build();

        usersJpa.save(userEntity);

        UserDTO createdUser = UserMapper.INSTANCE.userEntityToUserDTO(userEntity);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH:mm");
        createdUser.setCreatedDate(createdUser.getRegDate().format(dateTimeFormatter));

        return createdUser;
    }

    public String login(LoginDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.createToken(email);
        }catch (InternalAuthenticationServiceException e){//이메일 없을시 InternalAuthenticationServiceException 발생
            throw new NotFoundException(e.getMessage());
        }catch (BadCredentialsException e) {
            throw new NotAcceptException("비밀번호가 틀립니다.");
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
