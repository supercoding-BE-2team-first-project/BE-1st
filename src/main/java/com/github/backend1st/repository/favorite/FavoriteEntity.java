package com.github.backend1st.repository.favorite;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.users.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorite")
@EqualsAndHashCode(of = "favoriteId")
public class FavoriteEntity {

    @Id @Column(name = "favorite_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postEntity;
}
