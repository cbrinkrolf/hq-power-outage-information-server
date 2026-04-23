package io.github.cbrinkrolf.informationserver.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "outages", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "local_date_time", "latitude", "longitude", "customers_affected" }) })
public class Outage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

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

}
