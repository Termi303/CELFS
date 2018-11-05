package com.spe.celfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CelfsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext app = SpringApplication.run(CelfsApplication.class, args);
                ((ConfigurableApplicationContext) app).close();
	}
}
