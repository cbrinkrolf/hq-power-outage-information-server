package io.github.cbrinkrolf.informationserver.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reports")
public class Report {

	@Id
	private long id;

	@NotNull
	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP", unique = true)
	private LocalDateTime reportDateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getReportDateTime() {
		return reportDateTime;
	}

	public void setReportDateTime(LocalDateTime reportDateTime) {
		this.reportDateTime = reportDateTime;
	}

}
