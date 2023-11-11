package io.ylab.walletservice.in.wrapper;

import io.ylab.sharedwalletresource.core.dto.AuditDTO;
import io.ylab.starteraspectaudit.service.api.IAuditWrapperService;
import io.ylab.walletservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WrapperAudit implements IAuditWrapperService {

    private final IAuditService auditService;
    @Override
    public void create(AuditDTO auditDTO) {
        auditService.create(auditDTO);
    }
}
