package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.enums.UserRole;
import io.ylab.walletservice.dao.UserDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.AuditDao;
import io.ylab.walletservice.dao.factory.AuditDaoFactory;
import io.ylab.walletservice.dao.entity.UserEntity;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.factory.AuditServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class AuditServiceTest {
    private AuditDao auditDao;
    private AuditService auditService;
    private UserDao userDao;

    @BeforeEach
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        auditDao = (AuditDao) AuditDaoFactory.getInstance();
        auditService = (AuditService) AuditServiceFactory.getInstance();
        userDao = new UserDao();
    }

    @Test
    @DisplayName("Test for getting audit by login")
    void getAllByLogin() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("Have never been created account test1");
        userEntity.setPassword("1tset");
        userEntity.setRole(UserRole.USER);
        UserEntity savedUserEntity = userDao.save(userEntity);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setText("firstTestByUser");
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setUserId(savedUserEntity.getId());

        AuditEntity auditEntity2 = new AuditEntity();
        auditEntity2.setText("secondTestByUser");
        auditEntity2.setDtCreate(LocalDateTime.now());
        auditEntity2.setUserId(savedUserEntity.getId());

        auditDao.save(auditEntity);
        auditDao.save(auditEntity2);

        Set<AuditEntity> audits = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        audits.add(auditEntity);
        audits.add(auditEntity2);


        Assertions.assertEquals(audits, auditService.getAllByLogin(savedUserEntity.getLogin()));

        auditDao.delete(auditEntity.getId());
        auditDao.delete(auditEntity2.getId());
        userDao.delete(savedUserEntity.getId());
    }

    @Test
    @DisplayName("Test for creating audit")
    void createTest() {
        UserEntity userEntity = userDao.find("admin");

        AuditDTO auditDTO = new AuditDTO(userEntity.getId(),"firstTestByAdmin");

        AuditEntity entity = auditService.create(auditDTO);

        auditDao.delete(entity.getId());

        Assertions.assertEquals(auditDTO.getText(), entity.getText());
        Assertions.assertEquals(auditDTO.getUserId(), entity.getUserId());

    }
}
