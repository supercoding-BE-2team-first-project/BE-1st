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
@EqualsAndHashCode
public class UserEntity {
    @Id @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", length = 100)
    private String password;
}
