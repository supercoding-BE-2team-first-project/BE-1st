package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.web.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "email", source = "email")
//    @Mapping(target = "regDate", source = "regDate")

    UserDTO userEntityToUserDTO(UserEntity userEntity);

}
