package com.github.backend1st.service;

import com.github.backend1st.config.security.JwtTokenProvider;
import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UserRoles;
import com.github.backend1st.repository.users.UserRolesJpaRepository;
import com.github.backend1st.repository.users.UsersJpaRepository;
import com.github.backend1st.repository.users.roles.RolesEntity;
import com.github.backend1st.repository.users.roles.RolesJpaRepository;
import com.github.backend1st.service.exceptions.NotAcceptException;
import com.github.backend1st.service.exceptions.NotFoundException;
import com.github.backend1st.service.mapper.UserMapper;
import com.github.backend1st.web.dto.LoginDTO;
import com.github.backend1st.web.dto.SignUp;
import com.github.backend1st.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
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
public class SignUpLoginService {
    private final UsersJpaRepository usersJpaRepository;
    private final RolesJpaRepository rolesJpaRepository;
    private final UserRolesJpaRepository userRolesJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntityList = usersJpaRepository.findAll();
        return userEntityList.stream().map(UserMapper.INSTANCE::userEntityToUserDTO).collect(Collectors.toList());
    };
    public List<UserDTO> getAllUsersByAdmin(CustomUserDetails customUserDetails) {
        List<UserEntity> userEntityList = usersJpaRepository.findAll();
        return userEntityList.stream().map(UserMapper.INSTANCE::userEntityToUserDTO).collect(Collectors.toList());
    }


    @Transactional(transactionManager = "tmJpa")
    public UserDTO signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        if(usersJpaRepository.existsByEmail(email))return null;//이메일 같은게 존재할시 실패

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(password))//암호화
                .regDate(LocalDateTime.now())
                .build();
        //유저네임 패스워드 등록, 기본 ROLE_USER
        RolesEntity rolesEntity = rolesJpaRepository.findByName("ROLE_USER")//admin 로직 구현안함
                .orElseThrow(()-> new NotFoundException("DB에러"));

        usersJpaRepository.save(userEntity);
        userRolesJpaRepository.save(
                UserRoles.builder()
                        .rolesEntity(rolesEntity)
                        .userEntity(userEntity)
                        .build()
        );

        UserDTO createdUser = UserMapper.INSTANCE.userEntityToUserDTO(userEntity);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 - HH:mm");
        createdUser.setCreatedDate(createdUser.getRegDate().format(dateTimeFormatter));

        return createdUser;
    }

    public String login(LoginDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try{//email password 확인
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserEntity userEntity = usersJpaRepository.findByEmail(email)
                    .orElseThrow(()->new InternalAuthenticationServiceException(email));
            List<String> roles = userEntity.getUserRoles()
                    .stream().map((roleslist)->roleslist.getRolesEntity())
                    .map(role->role.getName()).collect(Collectors.toList());

            return jwtTokenProvider.createToken(email,roles);

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
