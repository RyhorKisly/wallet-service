package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.dao.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for Users
 */
@Mapper
public interface UserMapper {

    /**
     * Convert entity to dto
     * @param entity user
     * @return userDTO
     */
    UserDTO toDTO(UserEntity entity);

    /**
     * convert dto to entity
     * @param userCreateDTO user
     * @return entity
     */
    UserEntity toEntity(UserCreateDTO userCreateDTO);
}
