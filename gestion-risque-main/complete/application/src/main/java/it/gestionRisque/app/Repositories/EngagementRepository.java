package it.gestionRisque.app.Repositories;


import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Engagement;
@RepositoryRestResource
public interface EngagementRepository extends JpaRepository<Engagement, Long> {
	
	
	
	@Query( value= "select * from client  , engagement where engagement.id_client = client.id_client and to_char(client.reporting_date,'yyyy-MM-dd')=:daterepo",nativeQuery = true)	
 List<Engagement> findByDateReporting (@Param("daterepo")String daterepo);
 

}
