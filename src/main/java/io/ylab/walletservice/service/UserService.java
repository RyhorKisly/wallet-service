package io.ylab.walletservice.service;

import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserService;

import java.io.BufferedReader;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link IUserDao}
 * This an implementation of {@link IUserService}
 */
public class UserService implements IUserService {

    /**
     * Print when app can't find entity with entered params by user
     */
    private static final String WRONG_DATES = "Wrong login or password, try again";

    /**
     * define a field with a type {@link IUserDao} for further aggregation
     */
    private final IUserDao userDao;

    /**
     * Constructor initialize the Class UserService
     * @param userDao for initialization of the Class IUserDao
     */
    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Create entity.
     * Use the returned instance for further operations
     * @param userCreateDTO used to create entity
     * @return created entity
     */
    @Override
    public UserEntity create(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = convertToEntity(userCreateDTO);
        return userDao.save(userEntity);
    }

    /**
     * Get entity by login.
     * Check whether entity found or not.
     * @param login get entity by login
     * @return entity for farther interaction on app
     */
    @Override
    public UserEntity get(String login) {
        UserEntity userEntity = userDao.find(login);
        if(userEntity != null) {
            return userEntity;
        }
        System.out.println(WRONG_DATES);
        return null;
    }

    /**
     * Convert {@link UserCreateDTO} to {@link UserEntity}
     * @param dto used for conversion to {@link UserEntity}
     * @return converted {@link UserEntity}
     */
    private UserEntity convertToEntity(UserCreateDTO dto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin(dto.getLogin());
        userEntity.setPassword(dto.getPassword());
        userEntity.setRole(dto.getRole());
        return userEntity;
    }
}
