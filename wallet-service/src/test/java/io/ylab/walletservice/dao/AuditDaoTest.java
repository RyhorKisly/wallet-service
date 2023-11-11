package io.ylab.walletservice.dao;

import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.utils.IntegrationTestBase;
import io.ylab.walletservice.dao.entity.AuditEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class for testing methods of the class AuditDao")
public class AuditDaoTest extends IntegrationTestBase {

    /**
     * Define a field with a type {@link IAuditDao} for further use in the test
     */
    @Autowired
    private IAuditDao auditDao;

    @Test
    @DisplayName("Positive test for saving audit")
    void saveTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setText("test");
        AuditEntity savedAuditEntity = auditDao.save(auditEntity);

        assertEquals(auditEntity.getText(), savedAuditEntity.getText());
        assertEquals(auditEntity.getDtCreate(), savedAuditEntity.getDtCreate());
    }
}
