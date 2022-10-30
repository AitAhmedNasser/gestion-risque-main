package Auth.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Auth.entities.Agence;
import Auth.entities.Niveau;

@RepositoryRestResource
public interface AgenceRepository extends JpaRepository<Agence, Long>{

	public Agence findByAgenceName(String agenceName);

	public Agence getAgenceById(Long id_Agence);

}
