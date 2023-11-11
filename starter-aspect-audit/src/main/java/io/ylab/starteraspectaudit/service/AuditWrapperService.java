package io.ylab.starteraspectaudit.service;

import io.ylab.sharedwalletresource.core.dto.AuditDTO;
import io.ylab.starteraspectaudit.service.api.IAuditWrapperService;
import org.springframework.stereotype.Service;

/**
 * Stub class for. Used for creating configuration.
 */
@Service
public class AuditWrapperService implements IAuditWrapperService {

    /**
     * Implies method overriding
     * @param dto for saving in audit by aspects
     */
    @Override
    public void create(AuditDTO dto) {
        throw new RuntimeException("Not used wrapper");
    }
}
