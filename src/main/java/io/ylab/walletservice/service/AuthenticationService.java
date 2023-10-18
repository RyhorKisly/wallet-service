package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.AccountDTO;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserLoginDTO;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IAccountService;
import io.ylab.walletservice.service.api.IAuditService;
import io.ylab.walletservice.service.api.IUserAuthenticationService;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Class for generic operations on a service for users authentication.
 * Interact with {@link IUserService}, {@link IAccountDao ,} and {@link IAuditService}
 * This an implementation of {@link IUserAuthenticationService}
 */
@RequiredArgsConstructor
public class AuthenticationService implements IUserAuthenticationService {

    /**
     * Print when user enter wrong data when try to authorize
     */
    private static final String WRONG_DATES = "Wrong login or password, try again";

    /**
     * Print when user registered successfully
     */
    private static final String USER_REGISTERED = "User Registered";

    /**
     * Print when user signed in successfully
     */
    private static final String USER_SIGNED = "User signed in";

    /**
     * define a field with a type {@link IUserService} for further aggregation
     */
    private final IUserService userService;

    /**
     * define a field with a type {@link IAccountService} for further aggregation
     */
    private final IAccountService accountService;

    /**
     * define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;

    /**
     * Used to create user by registration
     * Create user and account. Account with random numberAccount(ID) and 0.0 balance.
     * @param dto used for registration user
     * @return {@link UserEntity}
     */
    @Override
    public UserEntity register(UserCreateDTO dto) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance(new BigDecimal("0.0"));
        accountDTO.setLogin(dto.getLogin());

        UserEntity userEntity = userService.create(dto);
        if(userEntity.getId() == null) {
            return userEntity;
        }

        accountService.create(accountDTO);

        AuditDTO auditDTO = new AuditDTO(userEntity.getId(), USER_REGISTERED);
        auditService.create(auditDTO);

        return userEntity;
    }

    /**
     * Used to authorize user.
     * Check whether data correct or not.
     * @param dto used for authorization user
     * @return {@link UserEntity}
     */
    @Override
    public UserEntity authorize(UserLoginDTO dto) {
        UserEntity userEntity = userService.get(dto.getLogin());
        if (userEntity != null) {
            if(!userEntity.getPassword().equals(encodePassword(dto.getPassword()))) {
                System.out.println(WRONG_DATES);
                return null;
            } else {
                AuditDTO auditDTO = new AuditDTO(userEntity.getId(), USER_SIGNED);
                auditService.create(auditDTO);
                return userEntity;
            }
        } else {
            return null;
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
