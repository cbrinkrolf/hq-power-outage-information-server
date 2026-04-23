package io.github.cbrinkrolf.informationserver.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.cbrinkrolf.informationserver.domain.Report;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {

}
