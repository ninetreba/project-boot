package com.accenture.russiaatc.irentservice10.SNAPSHOT;

import com.accenture.russiaatc.irentservice10.SNAPSHOT.configuration.PriceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PriceProperties.class)
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

}
