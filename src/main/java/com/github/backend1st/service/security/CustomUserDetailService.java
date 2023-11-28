package com.github.backend1st.service.security;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpa;
import com.github.backend1st.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UsersJpa usersJpa;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity= usersJpa.findByEmail(email)
                .orElseThrow(()->new NotFoundException("\""+email+"\" 해당 이메일의 유저가 없습니다."));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
//                .authorities(Arrays.asList("USER"))//미구현
                .build();
        return customUserDetails;
    }
}
