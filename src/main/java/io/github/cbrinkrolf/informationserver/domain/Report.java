package io.github.cbrinkrolf.informationserver.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

	@ManyToMany
	@JoinTable(name = "reports_to_outages", joinColumns = { @JoinColumn(name = "report_id") }, inverseJoinColumns = {
			@JoinColumn(name = "outage_id") })
	private Set<Outage> outages = new HashSet<>();

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

	public Set<Outage> getOutages() {
		return outages;
	}

	public void setOutages(Set<Outage> outages) {
		this.outages = outages;
	}

}
