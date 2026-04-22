package io.github.cbrinkrolf.informationserver.domain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TestRunner implements CommandLineRunner {
	public void run(String... args) {
		System.out.println("RUNNER");
	}
}
