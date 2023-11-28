package com.github.backend1st.repository.users;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@Builder

@Entity
@Table(name="users")
@NoArgsConstructor
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
