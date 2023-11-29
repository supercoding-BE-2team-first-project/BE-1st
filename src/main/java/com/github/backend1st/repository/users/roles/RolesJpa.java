package com.github.backend1st.repository.users.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesJpa extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByName(String name);
}