package io.github.cbrinkrolf.informationserver.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
//@Table(uniqueConstraints = {
//		@UniqueConstraint(columnNames = { "local_date_time", "latitude", "longitude", "customers_affected" }) })
@Table(name = "outages")
public class Outage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "customers_affected")
	private long customersAffected;

	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime startDateTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof Outage)) {
			return false;
		}

		Outage outage = (Outage) o;

		return this.longitude == outage.getLongitude() && this.latitude == outage.getLatitude()
				&& this.customersAffected == outage.getCustomersAffected()
				&& this.startDateTime.equals(outage.getStartDateTime());
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

}
