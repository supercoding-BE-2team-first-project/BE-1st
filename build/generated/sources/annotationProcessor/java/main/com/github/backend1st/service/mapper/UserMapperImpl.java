package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.web.dto.UserDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-30T01:15:41+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setEmail( userEntity.getEmail() );
        userDTO.setRegDate( userEntity.getRegDate() );

        return userDTO;
    }
}
