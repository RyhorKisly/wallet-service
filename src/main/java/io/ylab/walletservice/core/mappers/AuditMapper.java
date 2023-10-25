package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;
import org.mapstruct.Mapper;

/**
 * Mapper for Audit
 */
@Mapper
public interface AuditMapper {

    /**
     * Convert entity to dto
     * @param auditEntity audit
     * @return AuditDTO
     */
    AuditDTO toDTO(AuditEntity auditEntity);
}
