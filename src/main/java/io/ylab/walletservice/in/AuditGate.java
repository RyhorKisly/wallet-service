package io.ylab.walletservice.in;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.dao.entity.AuditEntity;
import io.ylab.walletservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * Class fo interaction between {@link IAuditService} and {@link Menu}
 */
@RequiredArgsConstructor
public class AuditGate {

    /**
     * With this message we show to user which one audit user will see.
     */
    private static final String MESSAGE_TO_USER = "Audit for User: ";

    /**
     * Show audit data (Date, text message)
     */
    private static final String PRINT_AUDIT_DATA = "Date: %s\n Text: %s\n";

    /**
     * define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;

    /**
     * Find set of entities by login of the user and print it in console.
     * @param login find entity by user login
     */
    public void getUserAudit(String login) {
        Set<AuditEntity> audits = auditService.getAllByLogin(login);
        System.out.println(MESSAGE_TO_USER + login);
        for (AuditEntity audit : audits) {
            System.out.printf(PRINT_AUDIT_DATA,
                    audit.getDtCreate(),
                    audit.getText()
            );
        }
        System.out.println();
    }

    /**
     * Saves a given entity.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     * @param auditDTO used to transfer it in {@link IAuditService}
     */
    public void create(AuditDTO auditDTO) {
        auditService.create(auditDTO);
    }
}
