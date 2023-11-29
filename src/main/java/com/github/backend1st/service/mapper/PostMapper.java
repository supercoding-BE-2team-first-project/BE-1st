package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.posts.PostEntity;
import com.github.backend1st.web.dto.Post;
import com.github.backend1st.web.dto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    // 싱글톤
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    // 메소드

    @Mapping(source = "userEntity.userId", target = "userId")
    Post postEntityToPost(PostEntity postEntity);
}
