package io.ylab.walletsevice.core.mappers;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.core.mappers.AuditMapper;
import io.ylab.walletservice.core.mappers.AuditMapperImpl;
import io.ylab.walletservice.core.mappers.UserMapper;
import io.ylab.walletservice.dao.entity.AuditEntity;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Class for testing mappers for Audit DTOs and entities")
public class AuditMapperTest {

    /**
     * Define a field with a type {@link UserMapper} for further use in the test
     */
    private AuditMapper auditMapper;

    @BeforeAll
    @DisplayName("Initialize classes for tests")
    public void setUp() {
        this.auditMapper = new AuditMapperImpl();
    }

    @Test
    @DisplayName("Positive test for mapping auditEntity to AuditDTO")
    void toDTOTest() {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(10L);
        auditEntity.setText("user created");


        AuditDTO auditDTO = auditMapper.toDTO(auditEntity);

        Assertions.assertNotNull(auditDTO);

        Assertions.assertEquals(auditDTO.getText(), auditDTO.getText());
    }
}
