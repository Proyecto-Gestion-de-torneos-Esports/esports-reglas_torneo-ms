package Esports.Reglas_Torneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableFeignClients
@SpringBootApplication
@EnableMethodSecurity
public class ReglasTorneoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReglasTorneoApplication.class, args);
	}

}
