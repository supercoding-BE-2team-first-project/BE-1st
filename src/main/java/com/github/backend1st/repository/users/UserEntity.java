package com.github.backend1st.repository.users;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@Builder

@Entity
@Table(name="users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)//양방향 관계에서 연결될 필드값
    private Collection<UserRoles> userRoles;//⬆️LAZY 설정시 proxy에러

}
