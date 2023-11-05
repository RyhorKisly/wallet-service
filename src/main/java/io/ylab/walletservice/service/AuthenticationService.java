package io.ylab.walletservice.service;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.dto.UserDTO;
import io.ylab.walletservice.core.exceptions.NotExistUserException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.in.utils.JWTTokenHandler;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Class for generic operations on a service for users authentication.
 * Interact with {@link IUserService}, {@link IAccountDao ,} and {@link IAuditService}
 * This an implementation of {@link IUserAuthenticationService}
 */
@Service
@RequiredArgsConstructor
@Loggable
public class AuthenticationService implements IUserAuthenticationService {

    /**
     * Print when user enter wrong data when try to authorize
     */
    private static final String WRONG_DATES = "Wrong login or password, try again";

    /**
     * define a field with a type {@link IUserService} for further aggregation
     */
    private final IUserService userService;

    /**
     * define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * Initialize a field with a type {@link JWTTokenHandler} for using jwt token into class
     */
    private final JWTTokenHandler handler;

    /**
     * initialize a field with a type {@link UserMapper} for converting userDTO and entity
     */
    private final UserMapper userMapper;

    /**
     * Used to create user by registration
     * Create user and account. Account with random numberAccount(ID) and 0.0 balance.
     * @param dto used for registration user
     * @return {@link UserEntity}
     */
    @Override
    public UserEntity register(UserAuthenticationDTO dto) {
        String password = encodePassword(dto.getPassword());
        dto.setPassword(password);
        UserEntity userEntity = userService.createByRegistration(dto);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(new BigDecimal("0.0"));
        accountDTO.setId(userEntity.getId());

        accountService.create(accountDTO);

        return userEntity;
    }

    /**
     * Used to authorize user.
     * Check whether data correct or not.
     * @param dto used for authorization user
     * @return jwt token
     * @throws NotExistUserException when user does not exist in db
     */
    @Override
    public String authorize(UserAuthenticationDTO dto) {
        UserEntity userEntity = userService.get(dto.getLogin());
        if (userEntity != null) {
            if(!userEntity.getPassword().equals(encodePassword(dto.getPassword()))) {
                throw new NotExistUserException(WRONG_DATES);
            } else {
                UserDTO userDTO = userMapper.toDTO(userEntity);
                return handler.generateUserAccessToken(userDTO, userDTO.getLogin());
            }
        } else {
            throw new NotExistUserException(WRONG_DATES);
        }
    }

    /**
     *  Method for encoding password
     * @param password encode password for comparing
     * @return encoded password
     */
    private String encodePassword(String password) {
        return new StringBuffer(password).reverse().toString();
    }

}
