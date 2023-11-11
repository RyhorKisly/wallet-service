package io.ylab.starteraspectaudit.service.api;

import io.ylab.sharedwalletresource.core.dto.AuditDTO;

/**
 * Basic class for overriding methods in different projects for auditing
 */
public interface IAuditWrapperService {

    /**
     * Used for creating audit in order to add in audit
     */
    void create(AuditDTO dto);
}
