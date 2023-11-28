package com.github.backend1st.repository.users;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ValueGenerationType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@EqualsAndHashCode(of = "userId")
public class UserEntity {
    @Id @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;
}
