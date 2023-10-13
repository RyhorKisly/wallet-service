package io.ylab.walletsevice.dao.memory;

import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.dao.memory.AuditDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class AuditDaoTest {

    private AuditDao auditDao;

    @BeforeEach
    @DisplayName("Initialize class for tests")
    public void setUp() {
        auditDao = new AuditDao();
    }

    @Test
    @DisplayName("Test for finding all audits for user by login")
    void findAllByLoginAscByDTCreateTest() {
        AuditEntity auditEntity =
                new AuditEntity(UUID.randomUUID(), LocalDateTime.now(), "test6", "firstTestByUser");
        AuditEntity auditEntity2 =
                new AuditEntity(UUID.randomUUID(), LocalDateTime.now(), "test6", "secondTestByUser");

        auditDao.save(auditEntity);
        auditDao.save(auditEntity2);

        Set<AuditEntity> audits = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        audits.add(auditEntity);
        audits.add(auditEntity2);

        Set<AuditEntity> auditsTest = auditDao.findAllByLoginAscByDTCreate("test6");

        Assertions.assertEquals(audits, auditsTest);
    }

    @Test
    @DisplayName("Test for saving audit")
    void saveTest(){
        AuditEntity auditEntity = new AuditEntity(UUID.randomUUID(), LocalDateTime.now(), "test7", "firstTestByUser");

        AuditEntity savedEntity = auditDao.save(auditEntity);

        Assertions.assertEquals(auditEntity, savedEntity);
    }
}
