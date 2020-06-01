package microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("microservice.repository")
@EntityScan("microservice.model")
public class ClientFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientFrontendApplication.class, args);
	}

}
