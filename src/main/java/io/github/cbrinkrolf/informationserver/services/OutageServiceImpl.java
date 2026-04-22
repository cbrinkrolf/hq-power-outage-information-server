package io.github.cbrinkrolf.informationserver.services;

import org.springframework.stereotype.Service;

import io.github.cbrinkrolf.informationserver.repositories.OutageRepository;

@Service
public class OutageServiceImpl implements OutageService {

	private final OutageRepository outageRepository;

	public OutageServiceImpl(OutageRepository outageRepository) {
		this.outageRepository = outageRepository;
	}

}
