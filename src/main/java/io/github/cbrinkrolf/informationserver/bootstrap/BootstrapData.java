package io.github.cbrinkrolf.informationserver.bootstrap;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.cbrinkrolf.informationserver.domain.Outage;
import io.github.cbrinkrolf.informationserver.repositories.OutageRepository;
import io.github.cbrinkrolf.informationserver.services.OutageService;

@Component
public class BootstrapData implements CommandLineRunner {

	private final OutageRepository outageRepository;
	private final OutageService outageService;

	@Autowired
	public BootstrapData(OutageRepository outageRepository, OutageService outageService) {
		super();
		this.outageRepository = outageRepository;
		this.outageService = outageService;
		System.out.println("bootstrap data loaded");
	}

	// @Transactional
	@Override
	public void run(String... args) throws Exception {
		loadTestData();
	}

	private void loadTestData() {
		Outage o = new Outage();
		o.setLatitude(45.63760774828376);
		o.setLongitude(-74.03666800098304);
		o.setCustomersAffected(40);
		LocalDateTime start = LocalDateTime.of(2026, 12, 30, 13, 14, 15);
		o.setStartDateTime(start);

		Outage o1 = new Outage();
		o1.setLatitude(2);
		o1.setLongitude(3);
		o1.setCustomersAffected(41);
		// LocalDateTime start = LocalDateTime.of(2026, 12, 30, 13, 14, 15);
		o1.setStartDateTime(start);

		outageRepository.save(o);
		outageRepository.save(o1);
		System.out.println("outage saved");
	}
}
