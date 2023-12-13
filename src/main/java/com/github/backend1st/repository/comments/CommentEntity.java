package com.github.backend1st.repository.comments;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.users.UserEntity;
import javax.persistence.*;

import com.github.backend1st.web.dto.CommentDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "comments")
@EqualsAndHashCode(of = "commentId")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "post_id")
    private String postId;

    public CommentEntity(CommentDto commentDto) {
        this.content = commentDto.getContent();
        this.userId = commentDto.getUserId();
        this.postId = commentDto.getPostId();
    }
}
