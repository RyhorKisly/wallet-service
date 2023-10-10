package io.ylab.walletsevice.service;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.memory.AuditDao;
import io.ylab.walletservice.dao.memory.factory.AuditDaoFactory;
import io.ylab.walletservice.service.AuditService;
import io.ylab.walletservice.service.factory.AuditServiceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AuditServiceTest {
    private AuditDao auditDao;
    private AuditService auditService;

    @BeforeEach
    public void setUp() {
        auditDao = (AuditDao) AuditDaoFactory.getInstance();
        auditService = (AuditService) AuditServiceFactory.getInstance();
    }

    @Test
    void getAllByLogin() {
        AuditEntity auditEntity =
                new AuditEntity(UUID.randomUUID(), LocalDateTime.now(), "test01", "firstTestByUser");
        AuditEntity auditEntity2 =
                new AuditEntity(UUID.randomUUID(), LocalDateTime.now(), "test01", "secondTestByUser");

        auditDao.save(auditEntity);
        auditDao.save(auditEntity2);

        Set<AuditEntity> audits = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        audits.add(auditEntity);
        audits.add(auditEntity2);


        Assertions.assertEquals(audits, auditService.getAllByLogin(auditEntity.getUserLogin()));
    }

    @Test
    void createTest() {

        AuditDTO auditDTO = new AuditDTO("test1","firstTestByUser");

        AuditEntity entity = auditService.create(auditDTO);

        Assertions.assertEquals(auditDTO.getText(), entity.getText());
        Assertions.assertEquals(auditDTO.getUserLogin(), entity.getUserLogin());

    }
}
