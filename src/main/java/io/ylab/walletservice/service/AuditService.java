package io.ylab.walletservice.service;

import io.ylab.walletservice.aop.annotations.Loggable;
import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.api.IAccountDao;
import io.ylab.walletservice.dao.api.IAuditDao;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Class for generic operations on a service for an Audit.
 * Interact with {@link IAccountDao ,} and {@link IAuditService}
 * This an implementation of {@link IAuditService}
 */
@RequiredArgsConstructor
@Loggable
public class AuditService implements IAuditService {

    /**
     * define a field with a type {@link IAuditDao} for further aggregation
     */
    private final IAuditDao auditDao;

    /**
     * get set of entities by login of the user
     * @return set of entities for farther interaction with app
     */
    @Override
    public Set<AuditEntity> getAll() {
        return auditDao.findAllAscByDTCreate();
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * @param auditDTO used to create entity
     * @return created entity
     */
    @Override
    public AuditEntity create(AuditDTO auditDTO) {
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setDtCreate(LocalDateTime.now());
        auditEntity.setText(auditDTO.getText());
        return auditDao.save(auditEntity);
    }
}
