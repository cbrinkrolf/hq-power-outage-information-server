package io.github.cbrinkrolf.informationserver.bootstrap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.cbrinkrolf.informationserver.domain.Outage;
import io.github.cbrinkrolf.informationserver.repositories.OutageRepository;
import io.github.cbrinkrolf.informationserver.repositories.ReportRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@DataJpaTest
@Import(SQLDataImporterExporter.class)
@Transactional
class SQLDataImporterExporterTest {

	private final Path resourceDirectory = Paths.get("src", "test", "resources");

	@Autowired
	SQLDataImporterExporter sqlIO;// = new SQLDataImporterExporter();

	@Autowired
	OutageRepository outageRepository;

	@Autowired
	ReportRepository reportRepository;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	void testImportSQLInsertsBrokenFile() {

		System.out.println("outages from DB: " + outageRepository.findAll().size());

		// sqlIO.showTables();
		try {
			init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Path filePath = resourceDirectory.resolve("valid_inserts.sql");
		System.out.println(filePath.toAbsolutePath());
		// filePath.getn
		// System.out.println(filePath.to);
		// String fileName = "";
		assertTrue(sqlIO.importSQLInserts(filePath.toFile()));
		assertEquals(2, outageRepository.findAll().size());
		assertEquals(1, reportRepository.findAll().size());

		Session session = entityManager.unwrap(Session.class);
		System.out.println(session);

	}

	public void init() throws SQLException, IOException {
		System.out.println("start init");
		File f = resourceDirectory.resolve("show.sql").toFile();
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:dataSource", "sa", "password");
		ResultSet rs = RunScript.execute(connection, new FileReader(f));
		// log.info("Reading Data from the employee table");
		while (rs.next()) {
			// log.info("ID: {}, Name: {}", rs.getInt("id"), rs.getString("name"));
			System.out.println(rs.getString(1));
		}
		System.out.println("end init");
	}

	@Test
	void testImportSQLInsertsValidFile() {

		assertTrue(outageRepository.findAll().isEmpty());

	}

	@Test
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
	void insertTest() {
		Session session = entityManager.unwrap(Session.class);
		System.out.println(session);
		// sqlIO.showTables();

		System.out.println("outages from DB before saving: " + outageRepository.findAll().size());
		for (Outage outage : outageRepository.findAll()) {
			System.out.println("id: " + outage.getId());
			session.merge(outage);
			// outageRepository.save(outage);
			// entityManager.refresh(outage);
			// entityManager.flush();
			entityManager.merge(outage);
			// session.refresh(outage);
		}

		Outage o = new Outage();
		o.setLatitude(2);
		o.setLongitude(2);
		o.setCustomersAffected(49);
		LocalDateTime start = LocalDateTime.of(2026, 12, 30, 13, 14, 15);
		o.setStartDateTime(start);
		// System.out.println(o.getId());

		Outage o1 = new Outage();
		o1.setLatitude(3);
		o1.setLongitude(3);
		o1.setCustomersAffected(50);
		// LocalDateTime start = LocalDateTime.of(2026, 12, 30, 13, 14, 15);
		o1.setStartDateTime(start);
		// System.out.println(o1.getId());

		// session.refresh(o);
		// session.refresh(o1);
		// session.close();

		SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		// session = sessionFactory.openSession();

		System.out.println("outages from DB before saving: " + outageRepository.findAll().size());
		// session.merge(o);
		// session.merge(o1);
		System.out.println(outageRepository.save(o).getId());
		outageRepository.save(o1);
		System.out.println("outage saved");
		System.out.println("outages from DB: " + outageRepository.findAll().size());
	}

}
