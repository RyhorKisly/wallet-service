package io.ylab.walletservice.dao.api;

import io.ylab.walletservice.dao.entity.AuditEntity;

import java.util.Set;

/**
 * Interface for generic operations on a repository for Audit.
 */
public interface IAuditDao {

    /**
     * Find set of entities by id of the user
     * @return set of entities from storage
     */
    Set<AuditEntity> findAllAscByDTCreate();

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param auditEntity save given entity
     * @return the saved entity
     */
    AuditEntity save(AuditEntity auditEntity);

}
