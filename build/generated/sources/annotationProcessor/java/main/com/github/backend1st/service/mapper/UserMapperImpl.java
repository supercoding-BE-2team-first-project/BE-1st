package com.github.backend1st.service.mapper;

import com.github.backend1st.repository.users.UserEntity;
import com.github.backend1st.web.dto.UserDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-05T14:40:02+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.4.jar, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( userEntity.getUserId() != null ) {
            userDTO.setUserId( String.valueOf( userEntity.getUserId() ) );
        }
        userDTO.setUsername( userEntity.getUsername() );
        userDTO.setEmail( userEntity.getEmail() );

        return userDTO;
    }
}
