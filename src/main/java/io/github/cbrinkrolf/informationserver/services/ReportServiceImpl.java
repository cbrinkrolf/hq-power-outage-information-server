package io.github.cbrinkrolf.informationserver.services;

import org.springframework.stereotype.Service;

import io.github.cbrinkrolf.informationserver.repositories.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

	private final ReportRepository reportRepository;

	public ReportServiceImpl(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}

}
