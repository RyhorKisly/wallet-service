package io.ylab.walletservice.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Properties for using in places connected with jwt
 */
@Getter
@NoArgsConstructor
@Configuration
public class JWTProperties {

    /**
     * Secret key for creating token
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Issuer which will be encapsulated in token
     */
    @Value("${jwt.issuer}")
    private String issuer;

    /**
     * UserName for setting basic name for token which used by user
     */
    @Value("${jwt.user}")
    private String user;

    /**
     * System name for setting basic name for token which used by app
     */
    @Value("${jwt.system}")
    private String system;
}
