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
import com.github.backend1st.web.dto.*;
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
        String userName = signUpRequest.getUserName();
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();

        if(usersJpaRepository.existsByEmail(email)||
        usersJpaRepository.existsByUsername(signUpRequest.getUsername())){
            UserDTO userDTO = new UserDTO();
            userDTO.setMessage("같은 email 또는 username이 존재 합니다.");
            userDTO.setUsername("실패");
            userDTO.setEmail("실패");
            return userDTO;//이메일또는 이름 같은게 존재할시 실패
        }

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .userName(userName)
                .password(passwordEncoder.encode(password))//암호화
                .regDate(LocalDateTime.now())
                .username(signUpRequest.getUsername())
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
        createdUser.setMessage("회원가입이 성공적으로 완료되었습니다.");

        return createdUser;
    }

    public LoginResponse login(LoginDTO loginRequest) {
        String emailOrName = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        try{//email password 확인
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailOrName, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserEntity userEntity = usersJpaRepository.findByEmailOrUsername(emailOrName, emailOrName)
                    .orElseThrow(()->new InternalAuthenticationServiceException(emailOrName));
            List<String> roles = userEntity.getUserRoles()
                    .stream().map((roleslist)->roleslist.getRolesEntity())
                    .map(role->role.getName()).collect(Collectors.toList());

            String token = jwtTokenProvider.createToken(userEntity.getEmail(),roles);
            LoginResponse loginResponse = new LoginResponse();
            if(token!=null) {
                loginResponse.setMessage("로그인이 성공적으로 완료되었습니다.");
                loginResponse.setToken(token);
                loginResponse.setUserId(userEntity.getUserId());
                loginResponse.setUsername(userEntity.getUsername());
            }else{loginResponse.setMessage("로그인 실패");}

            return loginResponse;

        }catch (InternalAuthenticationServiceException e){//이메일 없을시 InternalAuthenticationServiceException 발생
            throw new NotFoundException(e.getMessage()+" InternalAuthenticationService 발생");
        }catch (BadCredentialsException e) {
            throw new NotAcceptException("비밀번호가 틀립니다.");
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    public DuplicateResponse checkEmail(String email, String username) {
        boolean duplicate = usersJpaRepository.existsByEmail(email);

        DuplicateResponse duplicateResponse = new DuplicateResponse();
        duplicateResponse.setDuplicate(duplicate);
        if(duplicate)duplicateResponse.setMessage("사용자명 또는 이메일이 이미 사용 중입니다.");
        else duplicateResponse.setMessage("사용 가능 합니다.");

        return duplicateResponse;
    }
}
