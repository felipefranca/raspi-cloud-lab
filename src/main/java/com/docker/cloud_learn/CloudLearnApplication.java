package com.docker.cloud_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class CloudLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudLearnApplication.class, args);
	}

}
