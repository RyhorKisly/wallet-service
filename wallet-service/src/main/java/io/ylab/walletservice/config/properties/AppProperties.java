package io.ylab.walletservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Common app properties
 */
@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    /**
     * Default schema for db
     */
    private String defaultSchema;

    /**
     * Pass to drop changelog to drop tables
     */
    private String changeLogDrop;
}
