package rs.ac.uns.ftn.findaroommate.FindARoommateServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FindARoommateServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindARoommateServerApplication.class, args);
	}

}
