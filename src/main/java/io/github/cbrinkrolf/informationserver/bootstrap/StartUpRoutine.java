package io.github.cbrinkrolf.informationserver.bootstrap;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

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
public class StartUpRoutine implements CommandLineRunner {

	private final SQLDataImporterExporter sqlIO;

	@Autowired
	private OutageRepository outageRepository;
	@Autowired
	private OutageService outageService;
	@Autowired
	private ReportRepository reportRepository;
	@Autowired
	private ReportService reportService;

	public StartUpRoutine(SQLDataImporterExporter sqlIO) {
		this.sqlIO = sqlIO;
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Start-up routine started");

		FileManager fm = new FileManager();

		List<Path> files = fm.getSortedSQLFilePaths(Path.of(System.getProperty("user.dir")));

		String fileName = files.get(files.size() - 1).toFile().getName();
		System.out.println(fileName);

		// sqlIO.importSQLInserts(new File("backup-20260424-024749_inserts.sql"));
		// loadTestData();
		outageRepository.findAll();
		sqlIO.exportSQLOnlyInserts();
		System.out.println("Start-up routine ended");

	}

	private void loadTestData() {
		Outage o = new Outage();
		o.setLatitude(45.63760774828376);
		o.setLongitude(-74.03666800098304);
		o.setCustomersAffected(47);
		LocalDateTime start = LocalDateTime.of(2026, 12, 30, 13, 14, 15);
		o.setStartDateTime(start);

		Outage o1 = new Outage();
		o1.setLatitude(2);
		o1.setLongitude(3);
		o1.setCustomersAffected(48);
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
