package io.ylab.walletservice.service;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.UserCreateDTO;
import io.ylab.walletservice.core.dto.UserAuthenticationDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.core.mappers.UserMapperImpl;
import io.ylab.walletservice.dao.api.IUserDao;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;

/**
 * Class for generic operations on a service for an Account.
 * Interact with {@link IUserDao}
 * This an implementation of {@link IUserService}
 */
@RequiredArgsConstructor
@Loggable
public class UserService implements IUserService {

    /**
     * define a field with a type {@link IUserDao} for further aggregation
     */
    private final IUserDao userDao;
    private final UserMapper userMapper = new UserMapperImpl();

    @Override
    public UserEntity createByUser(UserCreateDTO dto) {
        UserEntity userEntity = userMapper.toEntity(dto);
        return userDao.save(userEntity);
    }

    @Override
    public UserEntity createByRegistration(UserAuthenticationDTO dto) {
        UserEntity userEntity = convertToEntity(dto);
        return userDao.save(userEntity);
    }

    @Override
    public UserEntity get(String login) {
        return userDao.find(login);
    }

    @Override
    public UserEntity get(Long id) {
        return userDao.find(id);
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
}
