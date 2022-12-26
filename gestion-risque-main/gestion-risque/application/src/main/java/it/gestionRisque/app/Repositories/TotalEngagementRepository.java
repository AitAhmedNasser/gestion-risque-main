package it.gestionRisque.app.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Entities.RapportType;
@RepositoryRestResource
public interface TotalEngagementRepository  extends JpaRepository<RapportType,Long > {

	List<RapportType> findByCode(String code);

	List<RapportType> findByTypes(String type);
	
	@Query( value= "select  p.types from  parametrageRapport p group by p.types ")	
 	List<String> getTypes();

}
