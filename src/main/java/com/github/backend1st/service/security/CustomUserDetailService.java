package com.github.backend1st.service.security;

import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpaRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UsersJpaRepository usersJpaRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersJpaRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("\""+email+"\" 해당 이메일의 유저가 없습니다."));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getUserRoles().stream()
                        .map(rs->rs.getRolesEntity())
                        .map(r->r.getName()).collect(Collectors.toList()))
                .build();

        return customUserDetails;
    }
}
