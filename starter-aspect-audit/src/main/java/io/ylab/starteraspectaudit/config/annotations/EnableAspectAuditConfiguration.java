package io.ylab.starteraspectaudit.config.annotations;

import io.ylab.starteraspectaudit.config.AOPConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation for enabling use parts of this starter in different projects
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AOPConfig.class)
public @interface EnableAspectAuditConfiguration {
}
