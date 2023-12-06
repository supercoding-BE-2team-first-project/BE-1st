package com.github.backend1st.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmailOrUsername(String email, String name);

    boolean existsByEmail(String email);//이메일 같은지 확인
    boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
