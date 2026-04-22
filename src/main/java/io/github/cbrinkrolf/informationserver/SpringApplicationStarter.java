package io.github.cbrinkrolf.informationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringApplicationStarter {

	public static void main(String[] args) {
		System.out.println("start application ...");
		ConfigurableApplicationContext context = SpringApplication.run(SpringApplicationStarter.class, args);

		// context.close();
		System.out.println("... application ended");

	}

}
