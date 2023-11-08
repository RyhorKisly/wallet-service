package io.ylab.walletservice;

import io.ylab.starteraspectaudit.config.annotations.EnableAspectAuditConfiguration;
import io.ylab.walletservice.config.properties.JWTProperties;
import io.ylab.walletservice.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Enable properties of the app and AspectAuditConfiguration from starter-aspect-audit
 */
@SpringBootApplication
@EnableAspectAuditConfiguration
@EnableConfigurationProperties({JWTProperties.class, AppProperties.class})
public class WalletServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}
