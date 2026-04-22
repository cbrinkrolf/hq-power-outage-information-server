package io.github.cbrinkrolf.informationserver.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.github.cbrinkrolf.informationserver.domain.Outage;

@Repository
public interface OutageRepository extends CrudRepository<Outage, Long> {

}
