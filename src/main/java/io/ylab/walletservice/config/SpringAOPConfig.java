package io.ylab.walletservice.config;

import io.ylab.walletservice.aop.aspects.AuditableAspect;
import io.ylab.walletservice.aop.aspects.LoggableAspect;
import io.ylab.walletservice.service.api.IAuditService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class SpringAOPConfig {

    @Bean
    public AuditableAspect auditableAspect(IAuditService auditService) {
        return new AuditableAspect(auditService);
    }

    @Bean
    public LoggableAspect loggableAspect() {
        return new LoggableAspect();
    }

}
