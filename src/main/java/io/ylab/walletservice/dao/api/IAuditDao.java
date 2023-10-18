package io.ylab.walletservice.dao.api;

import io.ylab.walletservice.dao.entity.AuditEntity;

import java.util.Set;

/**
 * Interface for generic operations on a repository for Audit.
 */
public interface IAuditDao {

    /**
     * find set of entities by login of the user
     * @param login find entity by user login
     * @return set of entities from storage
     */
    Set<AuditEntity> findAllByLoginAscByDTCreate(String login);

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param auditEntity save given entity
     * @return the saved entity
     */
    AuditEntity save(AuditEntity auditEntity);

    /**
     * Method just for testing method save()
     * @param id for finding and deleting audit
     */
    void delete(Long id);

    /**
     * Method just for testing
     * @param userId for finding and deleting audit
     */
    void deleteByUserId(Long userId);
}
