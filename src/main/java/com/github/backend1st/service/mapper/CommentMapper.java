package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.comments.CommentEntity;
import com.github.backend1st.web.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDto commentEntityToCommentDto(CommentEntity commentEntity);

}
