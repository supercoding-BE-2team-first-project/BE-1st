package com.github.backend1st.repository.comments;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commnets")
@EqualsAndHashCode(of = "commentId")
public class CommentEntity {

    @Id @Column(name = "comment_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "create_at", nullable = false)
    private String createAt;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "post_id")
    private Integer postId;
}
