package io.ylab.starteraspectaudit.config;


import io.ylab.starteraspectaudit.aop.AuditableAspect;
import io.ylab.starteraspectaudit.service.AuditWrapperService;
import io.ylab.starteraspectaudit.service.api.IAuditWrapperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration class for configuring beans in this starter
 */
@Configuration
@EnableAspectJAutoProxy
@Slf4j
public class AOPConfig {

    /**
     * Methods of the class {@link IAuditWrapperService} have to be overriding for auditing
     */
    @Bean
    @ConditionalOnMissingBean
    public IAuditWrapperService auditWrapperService() {
        return new AuditWrapperService();
    }

    /**
     * Creating stub for {@link IAuditWrapperService}
     * Write logs into info
     * @param auditWrapperService stub object. Have to be overriding with methods
     * @return Aspect used for saving audits
     */
    @Bean
    public AuditableAspect auditableAspect(IAuditWrapperService auditWrapperService) {
        log.info("Creating AuditableAspect bean");
        return new AuditableAspect(auditWrapperService);
    }

}
