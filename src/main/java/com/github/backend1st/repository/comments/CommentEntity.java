package com.github.backend1st.repository.comments;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.users.UserEntity;
import javax.persistence.*;

import com.github.backend1st.web.dto.CommentDto;
import lombok.*;

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

    @Column(name = "create_at", nullable = false)
    private String createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;

    public CommentEntity(CommentDto commentDto) {
        this.content = commentDto.getContent();
        this.userEntity.setUserId(Integer.valueOf(commentDto.getUserId()));
        this.postEntity.setPostId(Integer.valueOf(commentDto.getPostId()));
    }
}
