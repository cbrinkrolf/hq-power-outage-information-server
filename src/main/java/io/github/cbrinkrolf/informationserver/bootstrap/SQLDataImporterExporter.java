package io.github.cbrinkrolf.informationserver.bootstrap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SQLDataImporterExporter {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUser;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	public void exportSQL() {
		String backupFile = "backup-"
				+ java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
				+ ".sql";
		exportSQL(backupFile);

	}

	public void exportSQL(String fileName) {
		System.out.println("start SQL export");

		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement ps = con.prepareStatement("SCRIPT TO '" + fileName + "'")) {
			// ps.executeUpdate();
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished SQL export");
	}

	public void exportSQLOnlyInserts() {

		StringBuilder sb = new StringBuilder();
		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement ps = con.prepareStatement("SCRIPT SIMPLE NOSETTINGS")) {// TO '" + backupFile + "'"))
																							// {
			// ps.executeUpdate();
			ResultSet resultSet = ps.executeQuery();
			// response.setContentType("text/plain");
			// ServletOutputStream out = response.getOutputStream();

			while (resultSet.next()) {

				String columnValue = resultSet.getString(1);
				System.out.println("colValue: " + columnValue);
				// System.out.println(resultSet.getString(0));
				if (columnValue.startsWith("INSERT")) {
					System.out.print(columnValue);
					sb.append(columnValue);
					System.out.println();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (sb.isEmpty() || sb.toString().isBlank()) {
			return;
		}

		String backupFile = "backup-"
				+ java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
				+ "_inserts.sql";
		File f = new File(backupFile);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			writer.write(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showTables() {
		System.out.println("begin show tables");
		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement ps = con.prepareStatement("SHOW TABLES")) {// TO '" + backupFile + "'"))
																				// {
			// ps.executeUpdate();
			ResultSet resultSet = ps.executeQuery();
			// response.setContentType("text/plain");
			// ServletOutputStream out = response.getOutputStream();

			while (resultSet.next()) {

				String columnValue = resultSet.getString(1);
				System.out.println("colValue: " + columnValue);
				// System.out.println(resultSet.getString(0));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("end show tables");
	}

	public boolean importSQLInserts(File file) {

		try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
			ResultSet rs = RunScript.execute(connection, new FileReader(file));
			// log.info("Reading Data from the employee table");
			while (rs != null && rs.next()) {
				// log.info("ID: {}, Name: {}", rs.getInt("id"), rs.getString("name"));
				System.out.println(rs.getString(1));
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		// System.out.println("end init");

		String fileName = "";
		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement ps = con.prepareStatement("RUNSCRIPT from '" + fileName + "'")) {
			// ps.executeUpdate();
			// ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public void exportDBZip() {
		System.out.println("start zip backup");
		String backupFile = "backup-"
				+ java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
				+ ".zip";
		try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				PreparedStatement ps = con.prepareStatement("BACKUP TO '" + backupFile + "'")) {
			// ps.executeUpdate();
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished zip export");
	}
}
