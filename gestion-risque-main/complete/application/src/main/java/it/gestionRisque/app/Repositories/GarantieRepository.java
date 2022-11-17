package it.gestionRisque.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Garantie;
@RepositoryRestResource
public interface GarantieRepository extends JpaRepository<Garantie, Long>{

}
