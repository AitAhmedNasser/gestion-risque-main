package it.gestionRisque.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Impaye;
@RepositoryRestResource
public interface ImpayeRepository extends JpaRepository<Impaye, Long> {

}
