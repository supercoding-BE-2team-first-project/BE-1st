package com.github.backend1st.repository.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findAllByPostId(String postId);
}
