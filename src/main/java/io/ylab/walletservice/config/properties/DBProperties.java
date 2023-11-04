package io.ylab.walletservice.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Properties for using in places connected with database
 */
@Getter
@NoArgsConstructor
@Component
public class DBProperties {

    /**
     * Name of driver used for connecting with db
     */
    @Value("${database.driver-class-name}")
    private String driverClassName;

    /**
     * Url at which interaction with the database will be carried out
     */
    @Value("${database.url}")
    private String url;

    /**
     * UserName for entering in database
     */
    @Value("${database.user}")
    private String UserName;

    /**
     * Password for entering in database
     */
    @Value("${database.password}")
    private String Password;

    /**
     * Schema where will be created app tables and so on
     */
    @Value("${database.default-schema}")
    private String defaultSchema;

    /**
     * Schema where will be created data of liquibase
     */
    @Value("${database.liquibase-schema}")
    private String liquibaseSchema;

    /**
     * File with all command for creating data in database using liquibase
     */
    @Value("${liquibase.changelog-file}")
    private String changelogFile;
}
