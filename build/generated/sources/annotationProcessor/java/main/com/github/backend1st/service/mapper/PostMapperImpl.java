package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.web.dto.Post;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-30T01:15:41+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public Post postEntityToPost(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }

        Post post = new Post();

        post.setUserId( postEntityUserEntityUserId( postEntity ) );
        post.setPostId( postEntity.getPostId() );
        post.setTitle( postEntity.getTitle() );
        post.setContent( postEntity.getContent() );
        post.setCreateAt( postEntity.getCreateAt() );
        post.setFavoriteCount( postEntity.getFavoriteCount() );
        post.setCommentCount( postEntity.getCommentCount() );

        return post;
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
}
