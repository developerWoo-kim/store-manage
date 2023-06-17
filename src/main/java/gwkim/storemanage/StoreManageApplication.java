package gwkim.storemanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StoreManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreManageApplication.class, args);
	}

}
