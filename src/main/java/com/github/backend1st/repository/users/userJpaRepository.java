package com.github.backend1st.repository.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userJpaRepository extends JpaRepository<UserEntity, Integer> {

}
