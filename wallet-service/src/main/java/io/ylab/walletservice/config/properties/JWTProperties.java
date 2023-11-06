package io.ylab.walletservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for using in places connected with jwt
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    /**
     * Secret key for creating token
     */
    private String secret;

    /**
     * Issuer which will be encapsulated in token
     */
    private String issuer;

    /**
     * UserName for setting basic name for token which used by user
     */
    private String user;

    /**
     * System name for setting basic name for token which used by app
     */
    private String system;
}
