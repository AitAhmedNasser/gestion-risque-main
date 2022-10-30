package Auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Auth.entities.Niveau;

@RepositoryRestResource
public interface niveauRepository extends JpaRepository<Niveau, Long>{


	public Niveau findNiveauByIdNiv(Long id_Niveau);
}
