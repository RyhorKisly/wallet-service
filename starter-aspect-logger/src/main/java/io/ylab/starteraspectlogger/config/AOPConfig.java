package io.ylab.starteraspectlogger.config;

import io.ylab.starteraspectlogger.aop.LoggableAspect;
import lombok.extern.slf4j.Slf4j;
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

    @Bean
    public LoggableAspect loggableAspect() {
        log.info("Creating LoggableAspect bean");
        return new LoggableAspect();
    }

}
