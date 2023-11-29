package com.github.backend1st.repository.users;

import com.github.backend1st.repository.posts.PostEntity;

import lombok.*;
import org.apache.catalina.User;
import org.hibernate.annotations.ValueGenerationType;

import javax.persistence.Entity;
import javax.persistence.Table;
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

}
