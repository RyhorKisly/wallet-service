package io.ylab.walletservice.service.api;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;

import java.util.List;

/**
 * Interface for generic operations on a service for an Audit.
 */
public interface IAuditService {

    /**
     * get list of entities by login of the user
     * @return list of entities for farther interaction with app
     */
    List<AuditEntity> getAll();

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * @param auditDTO used to create entity
     * @return created entity
     */
    AuditEntity create(AuditDTO auditDTO);
}
