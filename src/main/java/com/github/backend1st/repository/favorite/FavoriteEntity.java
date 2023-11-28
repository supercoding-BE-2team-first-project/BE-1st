package com.github.backend1st.repository.favorite;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorite")
@EqualsAndHashCode(of = "favorite_id")
public class FavoriteEntity {

    @Id @Column(name = "favorite_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "post_id")
    private Integer postId;
}
