package com.docker.raspi_cloud_lab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class RaspiCloudLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaspiCloudLabApplication.class, args);
	}

}
