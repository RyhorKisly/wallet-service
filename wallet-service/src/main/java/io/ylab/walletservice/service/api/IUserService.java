package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.dao.entity.UserEntity;

/**
 * Interface for generic operations on a service for Users.
 */
public interface IUserService {

    /**
     * Create entity.
     * Use the returned instance for further operations
     * @param userCreateDTO used to create entity
     * @return created entity
     */
    UserEntity createByUser(UserCreateDTO userCreateDTO);

    /**
     * Create entity.
     * Use the returned instance for further operations
     * @param userRegistrationDTO used to create entity
     * @return created entity
     */
    UserEntity createByRegistration(UserAuthenticationDTO userRegistrationDTO);

    /**
     * get entity by login
     * @param login get entity by login
     * @return entity for farther interaction on app
     */
    UserEntity get(String login);

    /**
     * get entity by login
     * @param id get entity by id
     * @return entity for farther interaction on app
     */
    UserEntity get(Long id);
}
