package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserLoginDTO;
import io.ylab.walletservice.dao.entity.UserEntity;

/**
 * Register and authorize Users
 */
public interface IUserAuthenticationService {

    /**
     * Used to create user by registration
     * @param userCreateDTO used for registration user
     * @return entity for farther interaction into app
     */
    UserEntity register(UserCreateDTO userCreateDTO);

    /**
     * Used to authorize user.
     * @param userLoginDTO used for authorization user
     * @return entity for farther interaction into app
     */
    UserEntity authorize(UserLoginDTO userLoginDTO);
}
