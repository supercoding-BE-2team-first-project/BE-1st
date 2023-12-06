package com.github.backend1st.service;

import com.github.backend1st.repository.favorite.FavoriteEntity;
import com.github.backend1st.repository.favorite.FavoriteJpaRepository;
import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.posts.PostJpaRepository;
import com.github.backend1st.repository.user_details.CustomUserDetails;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.repository.users.UsersJpaRepository;
import com.github.backend1st.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostJpaRepository postJpaRepository;
    private final UsersJpaRepository usersJpaRepository;
    private final FavoriteJpaRepository favoriteJpaRepository;


    @Transactional(transactionManager = "tmJpa")
    public String registerLike(CustomUserDetails customUserDetails, Integer postId) {
        PostEntity postEntity = postJpaRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException("postId: " + postId + " 게시글을 찾을 수 없습니다."));

        UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("userId: " + customUserDetails.getUserId() + " 일치하는 유저정보를 찾을 수 없습니다."));

        FavoriteEntity favoriteEntity = favoriteJpaRepository.findAll().stream()
                .filter(f -> f.getUserEntity().equals(userEntity))
                .filter(f -> f.getPostEntity().equals(postEntity))
                .findFirst().orElse(null);

        if (favoriteEntity != null) {
            throw new RuntimeException("이미 좋아요를 등록하였습니다.");
        }else {
            postEntity.setFavoriteCount(postEntity.getFavoriteCount() + 1);
            FavoriteEntity addFavoriteEntity = FavoriteEntity.builder()
                    .postEntity(postEntity).userEntity(userEntity).build();
            favoriteJpaRepository.save(addFavoriteEntity);
            return "게시글에 좋아요가 추가되었습니다.";
        }
    }

    @Transactional(transactionManager = "tmJpa")
    public String deleteLike(CustomUserDetails customUserDetails, Integer postId) {
        PostEntity postEntity = postJpaRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException("postId: " + postId + " 게시글을 찾을 수 없습니다."));

        UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("userId: " + customUserDetails.getUserId() + " 일치하는 유저정보를 찾을 수 없습니다."));

        FavoriteEntity favoriteEntity = favoriteJpaRepository.findAll().stream()
                .filter(f -> f.getUserEntity().equals(userEntity))
                .filter(f -> f.getPostEntity().equals(postEntity))
                .findFirst().orElse(null);

        if (favoriteEntity == null) {
            throw new RuntimeException("아직 좋아요가 추가되지 않았습니다.");
        }else {
            postEntity.setFavoriteCount(postEntity.getFavoriteCount() - 1);
            favoriteJpaRepository.delete(favoriteEntity);
            return "게시글에 대한 좋아요가 취소되었습니다.";
        }
    }


    public boolean checklike(CustomUserDetails customUserDetails, Integer postId) {
        PostEntity postEntity = postJpaRepository.findById(postId)
                .orElseThrow(()-> new NotFoundException("postId: " + postId + " 게시글을 찾을 수 없습니다."));

        UserEntity userEntity = usersJpaRepository.findById(customUserDetails.getUserId())
                .orElseThrow(() -> new NotFoundException("userId: " + customUserDetails.getUserId() + " 일치하는 유저정보를 찾을 수 없습니다."));

        FavoriteEntity favoriteEntity = favoriteJpaRepository.findAll().stream()
                .filter(f -> f.getUserEntity().equals(userEntity))
                .filter(f -> f.getPostEntity().equals(postEntity))
                .findFirst().orElse(null);

        return favoriteEntity != null;
    }
}
