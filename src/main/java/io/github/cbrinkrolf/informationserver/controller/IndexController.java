package io.github.cbrinkrolf.informationserver.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/export")
	public void getIndex() {
		System.out.println("get was called");

		System.out.println("start export");

		String backupFile = "backup-"
				+ java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
				+ ".zip";
		try (Connection con = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "password");
				PreparedStatement ps = con.prepareStatement("SCRIPT TO '" + backupFile + "'")) {
			// ps.executeUpdate();
			ResultSet results = ps.executeQuery();
			System.out.println(results);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
