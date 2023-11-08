package io.ylab.starteraspectaudit.aop;

import io.ylab.sharedwalletresource.core.dto.AuditDTO;
import io.ylab.starteraspectaudit.service.api.IAuditWrapperService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Class for creating audit
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditableAspect {

    /**
     * Define a field with a type {@link IAuditWrapperService} for further aggregation
     */
    private final IAuditWrapperService auditWrapperService;

    /**
     * pointcut which say that methods or classes marked with annotation {@link io.ylab.starteraspectaudit.aop.annotations.Auditable}
     * have to be executed
     */
    @Pointcut("within(@io.ylab.starteraspectaudit.aop.annotations.Auditable *) && execution(* *(..))")
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
        dto.setDtCreate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
        auditWrapperService.create(dto);
        return result;
    }
}
