package Auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Auth.entities.Ressource;

@RepositoryRestResource
public interface RessourceRepository extends JpaRepository<Ressource, Long> {

	public Ressource findRessourceByName(String name);
	public Ressource getRessourceById(Long id_Ressources);
}
