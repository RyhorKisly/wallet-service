package io.ylab.walletservice.dao.memory;

import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.entity.AuditEntity;

import java.util.*;

/**
 * Class for generic operations on a repository for an Audit.
 * This an implementation of {@link IAuditDao}
 */
public class AuditDao implements IAuditDao {

    /**
     * Stores a map of entities. Key - ID, value - entity.
     */
    Map<UUID, AuditEntity> audits = new HashMap<>();

    /**
     * find set of entities by login of the user
     * @param login find entity by user login
     * @return set of entities from {@code audits}
     */
    @Override
    public Set<AuditEntity> findAllByLoginAscByDTCreate(String login) {
        Set<AuditEntity> audit = new TreeSet<>(Comparator.comparing(AuditEntity::getDtCreate));
        for (AuditEntity value : audits.values()) {
            if(value.getUserLogin().equals(login)) {
                audit.add(value);
            }
        }
        return audit;
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param auditEntity save given entity in {@code audits}
     * @return saved entity from {@code audits}
     */
    @Override
    public AuditEntity save(AuditEntity auditEntity) {
        audits.put(auditEntity.getUuid(), auditEntity);
        return auditEntity;
    }
}
