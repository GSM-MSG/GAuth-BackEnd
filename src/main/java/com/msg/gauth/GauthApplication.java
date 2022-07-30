package com.msg.gauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GauthApplication.class, args);
	}

}
