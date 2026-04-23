package io.github.cbrinkrolf.informationserver.bootstrap;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.cbrinkrolf.informationserver.domain.Outage;
import io.github.cbrinkrolf.informationserver.domain.Report;
import io.github.cbrinkrolf.informationserver.repositories.OutageRepository;
import io.github.cbrinkrolf.informationserver.repositories.ReportRepository;
import io.github.cbrinkrolf.informationserver.services.OutageService;
import io.github.cbrinkrolf.informationserver.services.ReportService;

@Component
public class BootstrapData implements CommandLineRunner {

	private final OutageRepository outageRepository;
	private final OutageService outageService;
	private final ReportRepository reportRepository;
	private final ReportService reportService;

	@Autowired
	public BootstrapData(OutageRepository outageRepository, OutageService outageService,
			ReportRepository reportRepository, ReportService reportService) {
		super();
		this.outageRepository = outageRepository;
		this.outageService = outageService;
		this.reportRepository = reportRepository;
		this.reportService = reportService;
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		loadTestData();
		System.out.println("bootstrap data loaded");
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

		Report r1 = new Report();
		r1.setId(20180418084019L);
		r1.getOutages().add(o);
		r1.getOutages().add(o1);
		r1.setReportDateTime(start);

		reportRepository.save(r1);

		for (Report r : reportRepository.findAll()) {
			for (Outage out : r.getOutages()) {
				System.out.println(out.equals(o));
			}
		}

	}
}
