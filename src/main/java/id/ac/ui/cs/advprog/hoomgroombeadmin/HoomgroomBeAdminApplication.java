package id.ac.ui.cs.advprog.hoomgroombeadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HoomgroomBeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoomgroomBeAdminApplication.class, args);
	}

}
