//package Auth.Repository;
package it.gestionRisque.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.Niveau;

@RepositoryRestResource
public interface niveauRepository extends JpaRepository<Niveau, Long>{


	public Niveau findNiveauByIdNiv(Long id_Niveau);
}
