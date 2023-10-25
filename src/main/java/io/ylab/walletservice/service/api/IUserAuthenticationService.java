package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.dao.entity.UserEntity;

/**
 * Register and authorize Users
 */
public interface IUserAuthenticationService {

    /**
     * Used to create user by registration
     * @param userAuthenticationDTO used for registration user
     * @return entity for farther interaction into app
     */
    UserEntity register(UserAuthenticationDTO userAuthenticationDTO);

    /**
     * Used to authorize user.
     * @param userAuthenticationDTO used for authorization user
     * @return entity for farther interaction into app
     */
    String authorize(UserAuthenticationDTO userAuthenticationDTO);
}
