package com.github.backend1st.repository.users;

<<<<<<< HEAD
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
=======
import com.github.backend1st.repository.posts.PostEntity;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.ValueGenerationType;

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
    @Id @Column(name = "user_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @OneToMany(mappedBy = "userEntity")
    private List<PostEntity> postEntities;
>>>>>>> 9ef8bf046ed965d394c82767e6055318110e9f21

}
