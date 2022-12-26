package it.gestionRisque.app.Repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.gestionRisque.app.Entities.Engagement;
@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long>,JpaSpecificationExecutor<Engagement> {



	@Query(value = "SELECT * FROM engagement, client  WHERE engagement.id_client = client.id_client AND  to_char(client.reporting_date,'yyyy-MM-dd') = :#{#reportingDate}", nativeQuery = true)
	Collection<Engagement> findAllEngagementClient(@Param("reportingDate") String reportingDate);

@Query( value= "select * from client  , engagement where engagement.id_client = client.id_client and to_char(client.reporting_date,'yyyy-MM-dd')=:daterepo",nativeQuery = true)	
	List<Engagement> findByDateReporting (@Param("daterepo")String daterepo);


	/*
	 * @Query( value=
	 * "select  new it.gestionRisque.app.Repositories.EngagementRepository.total(sum (engagement.nominal_exposure)) from  engagement"
	 * ) Total findTotal(GenericSpesification genericSpesification);
	 * 
	 * class Total { Double total;
	 * 
	 * public Total(Double total) { this.total = total; }
	 * 
	 * public Double getTotal() { return total; } }
	 */
}
