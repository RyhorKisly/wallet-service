package io.ylab.walletservice.aop.aspects;

import io.ylab.walletservice.core.dto.AuditDTO;
import io.ylab.walletservice.service.api.IAuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Class for creating audit
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditableAspect {

    /**
     * Define a field with a type {@link IAuditService} for further aggregation
     */
    private final IAuditService auditService;


    /**
     * pointcut which say that methods or classes marked with annotation {@link io.ylab.walletservice.aop.annotations.Auditable}
     * have to be executed
     */
    @Pointcut("within(@io.ylab.walletservice.aop.annotations.Auditable *) && execution(* *(..))")
    public void annotatedByAuditable() {
    }

    /**
     * Aspect for creating audit
     */
    @Around("annotatedByAuditable()")
    public Object audit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        AuditDTO dto = new AuditDTO();
        dto.setText("Action was performed on the entity by method: " + proceedingJoinPoint.getSignature().toShortString());
        auditService.create(dto);
        return result;
    }
}
