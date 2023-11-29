package com.github.backend1st.repository.posts;

import com.github.backend1st.repository.users.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
@EqualsAndHashCode(of = "postId")
public class PostEntity {
    @Id @Column(name = "post_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    private String createAt;

    @Column(name = "favorite_count", nullable = false, columnDefinition = "DEFAULT 0")
    private Integer favoriteCount;

    @Column(name = "comment_count", nullable = false, columnDefinition = "DEFAULT 0")
    private Integer commentCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
