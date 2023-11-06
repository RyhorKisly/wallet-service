package io.ylab.walletservice;

import io.ylab.starteraspectaudit.config.annotations.EnableAspectAuditConfiguration;
import io.ylab.walletservice.config.properties.JWTProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableAspectAuditConfiguration
@EnableConfigurationProperties({JWTProperties.class})
public class WalletServiceApplication {

	public static void main(String[] args) {
		//TODO add tests
		//TODO addSpringDocks
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}
