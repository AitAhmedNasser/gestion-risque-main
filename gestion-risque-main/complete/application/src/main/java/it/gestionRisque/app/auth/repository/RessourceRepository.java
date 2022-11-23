//package Auth.Repository;
package it.gestionRisque.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.Ressource;

@RepositoryRestResource
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

	public Ressource findRessourceByName(String name);
	public Ressource getRessourceById(Long id_Ressources);
}
