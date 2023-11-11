package io.ylab.walletservice.service;

import io.ylab.starteraspectlogger.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.exceptions.NotExistUserException;
import io.ylab.walletservice.core.exceptions.NotUniqueException;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link IUserDao}
 * This an implementation of {@link IUserService}
 */
@Service
@RequiredArgsConstructor
@Loggable
public class UserService implements IUserService {

    /**
     * Message when try to find user, but user does not exist by id
     */
    private static final String USER_LOGIN_NOT_EXIST_RESPONSE = "User with such login does not exist!";

    /**
     * Message when try to find user, but user does not exist by id
     */
    private static final String USER_ID_NOT_EXIST_RESPONSE = "User with such id does not exist!";

    /**
     * Used when trying to save an entity, it is discovered that a user with the same login already exists
     */
    private static final String USER_LOGIN_EXIST = "Such login exist, try again!";

    /**
     * Message when try save user with existed login
     */
    private static final String USER_LOGIN_UNIQUE = "user_login_unique";

    /**
     * define a field with a type {@link IUserDao} for further aggregation
     */
    private final IUserDao userDao;
    private final UserMapper userMapper;

    @Override
    public UserEntity createByUser(UserCreateDTO dto) {
        UserEntity userEntity = userMapper.toEntity(dto);
        return saveOrThrow(userEntity);
    }

    @Override
    public UserEntity createByRegistration(UserAuthenticationDTO dto) {
        UserEntity userEntity = convertToEntity(dto);
        return saveOrThrow(userEntity);
    }

    @Override
    public UserEntity get(String login) {
        return userDao.find(login)
                .orElseThrow(() -> new NotExistUserException(USER_LOGIN_NOT_EXIST_RESPONSE));
    }

    @Override
    public UserEntity get(Long id) {
        return userDao.find(id)
                .orElseThrow(() -> new NotExistUserException(USER_ID_NOT_EXIST_RESPONSE));
    }

    /**
     * Convert {@link UserCreateDTO} to {@link UserEntity}
     * @param dto used for conversion to {@link UserEntity}
     * @return converted {@link UserEntity}
     */
    private UserEntity convertToEntity(UserAuthenticationDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(dto.getLogin());
        userEntity.setPassword(dto.getPassword());
        userEntity.setRole(UserRole.USER);
        return userEntity;
    }

    /**
     * Calls save method of accountDao.
     * @param userEntity for saving.
     * @return userEntity if not exist return user in db.
     * @throws NotExistUserException if user exist in db.
     * @throws RuntimeException other problem with db.
     */
    private UserEntity saveOrThrow(UserEntity userEntity) {
        try {
            userEntity = userDao.save(userEntity);
        } catch(RuntimeException ex) {
            if (ex.getMessage().contains(USER_LOGIN_UNIQUE)) {
                throw new NotUniqueException(USER_LOGIN_EXIST, ex);
            } else {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return userEntity;
    }
}
