package io.ylab.walletservice.core.mappers;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for Audit
 */
@Mapper(uses = {AuditMapper.class})
public interface AuditSetMapper {

    /**
     * Convert set audit entity to dto
     * @param entities set of audits
     * @return set of dto
     */
    Set<AuditDTO> toDTOSet(Set<AuditEntity> entities);
}
