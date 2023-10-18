package io.ylab.walletsevice.dao;

import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletsevice.testcontainers.config.ContainersEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuditDaoTest extends ContainersEnvironment {
    private AuditDao auditDao;
    private UserDao userDao;
    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        auditDao = new AuditDao();
        userDao = new UserDao();
    }

    @Test
    void saveTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setUserId(userEntity.getId());
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setText("test");
        AuditEntity savedAuditEntity = auditDao.save(auditEntity);

        auditDao.delete(savedAuditEntity.getId());
        userDao.delete(savedUserEntity.getId());

        assertEquals(auditEntity.getText(), savedAuditEntity.getText());
        assertEquals(auditEntity.getUserId(), savedAuditEntity.getUserId());
        assertEquals(auditEntity.getDtCreate(), savedAuditEntity.getDtCreate());
    }
}
