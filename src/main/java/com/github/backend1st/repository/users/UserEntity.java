package com.github.backend1st.repository.users;

import com.github.backend1st.repository.posts.PostEntity;

import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@EqualsAndHashCode(of = "userId")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.EAGER)//양방향 관계에서 연결될 필드값
    private Collection<UserRoles> userRoles;//⬆️LAZY 설정시 proxy에러

}
