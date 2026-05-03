package io.github.cbrinkrolf.informationserver.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.SQLInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
//@SQLInsert(sql = "INSERT INTO OUTAGES (id) VALUES (?) ON DUPLICATE KEY UPDATE id = VALUES(id);")
@Table(name = "outages", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "local_date_time", "latitude", "longitude", "customers_affected" }) })
public class Outage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "longitude")
	private double longitude;

	@NotNull
	@Column(name = "latitude")
	private double latitude;

	@NotNull
	@Column(name = "customers_affected", columnDefinition = "INTEGER")
	@Min(0)
	private long customersAffected;

	@NotNull
	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime startDateTime;

	@ManyToMany(mappedBy = "outages")
	private Set<Report> reports = new HashSet<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(long customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customersAffected, id, latitude, longitude, reports, startDateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Outage other = (Outage) obj;
		return customersAffected == other.customersAffected && Objects.equals(id, other.id)
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& Objects.equals(reports, other.reports) && Objects.equals(startDateTime, other.startDateTime);
	}

}
