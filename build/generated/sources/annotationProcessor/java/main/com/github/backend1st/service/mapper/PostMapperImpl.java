package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.web.dto.PostDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-08T16:21:55+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO postEntityToPost(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setUserId( postEntityUserEntityUserId( postEntity ) );
        postDTO.setPostId( postEntity.getPostId() );
        postDTO.setTitle( postEntity.getTitle() );
        postDTO.setContent( postEntity.getContent() );
        postDTO.setCreateAt( postEntity.getCreateAt() );
        postDTO.setFavoriteCount( postEntity.getFavoriteCount() );
        postDTO.setCommentCount( postEntity.getCommentCount() );

        return postDTO;
    }

    @Override
    public PostEntity postDTOToPostEntity(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        PostEntity.PostEntityBuilder postEntity = PostEntity.builder();

        postEntity.userEntity( postDTOToUserEntity( postDTO ) );
        postEntity.postId( postDTO.getPostId() );
        postEntity.title( postDTO.getTitle() );
        postEntity.content( postDTO.getContent() );
        postEntity.createAt( postDTO.getCreateAt() );
        postEntity.favoriteCount( postDTO.getFavoriteCount() );
        postEntity.commentCount( postDTO.getCommentCount() );

        return postEntity.build();
    }

    private Integer postEntityUserEntityUserId(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        UserEntity userEntity = postEntity.getUserEntity();
        if ( userEntity == null ) {
            return null;
        }
        Integer userId = userEntity.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    protected UserEntity postDTOToUserEntity(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.userId( postDTO.getUserId() );

        return userEntity.build();
    }
}
