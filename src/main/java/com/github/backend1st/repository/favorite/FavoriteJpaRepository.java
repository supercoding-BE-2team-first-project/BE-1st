package com.github.backend1st.repository.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteJpaRepository extends JpaRepository<FavoriteEntity, Integer> {
}
