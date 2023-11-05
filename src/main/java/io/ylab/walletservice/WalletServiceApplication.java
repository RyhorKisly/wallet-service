package io.ylab.walletservice;

import io.ylab.walletservice.config.properties.JWTProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties({JWTProperties.class})
public class WalletServiceApplication {

	public static void main(String[] args) {
		//TODO add mapstruct with plugin
		//TODO add change liquibase
		//TODO add change config datasource
		//TODO add change properties
		//TODO Аспекты аудита и логирования вынести в стартер, сделать отдельным модулем. Один стартер должен автоматически подключаться, второй через аннотацию @EnableXXX
		//TODO add tests
		//TODO addSpringDocks
		SpringApplication.run(WalletServiceApplication.class, args);
	}

}
