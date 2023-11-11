package io.ylab.walletservice.service;

import io.ylab.sharedwalletresource.core.dto.AuditDTO;
import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.service.api.IAuditService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DisplayName("Class for testing methods of the class AuditService")
public class AuditServiceTest {

    /**
     * Define a field with a type {@link IAuditDao} for further use in the test
     */
    @Mock
    private IAuditDao auditDao;

    /**
     * Define a field with a type {@link IAuditService} for further use in the test
     */
    private IAuditService auditService;

    @BeforeEach
    @DisplayName("Initialize auditService with mocked auditDao")
    public void setUp() {
        auditService = new AuditService(auditDao);
    }

    @Test
    @DisplayName("Test for getting audit by login")
    void getAllByLogin() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setText("firstTestByUser");
        auditEntity.setDtCreate(LocalDateTime.now());

        AuditEntity auditEntity2 = new AuditEntity();
        auditEntity2.setText("secondTestByUser");
        auditEntity2.setDtCreate(LocalDateTime.now());

        List<AuditEntity> audits = List.of(auditEntity, auditEntity2);
        when(auditDao.findAllAscByDTCreate()).thenReturn(audits);

        List<AuditEntity> foundlist = auditService.getAll();

        Assertions.assertEquals(audits, foundlist);
    }

    @Test
    @DisplayName("Positive test for creating audit")
    void createTest() {
        AuditDTO auditDTO = new AuditDTO();
        auditDTO.setText("Some text for audit");
        auditDTO.setDtCreate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setText(auditDTO.getText());
        auditEntity.setDtCreate(LocalDateTime.ofInstant(Instant.ofEpochMilli(auditDTO.getDtCreate()), ZoneId.systemDefault()));

        when(auditDao.save(auditEntity)).thenReturn(auditEntity);
        AuditEntity createdEntity = auditService.create(auditDTO);
        Assertions.assertEquals(auditEntity, createdEntity);
    }
}
