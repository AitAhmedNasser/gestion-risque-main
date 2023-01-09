package it.gestionRisque.app.Repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.portefeuil.ISum;

@Repository
public interface EngagementRepository extends JpaRepository<Engagement, Long>, JpaSpecificationExecutor<Engagement> {

	@Query(value = "select g from engagement g where to_char(g.client.reportingDate,'yyyy-MM-dd') = ?1 ")
	Collection<Engagement> findAllEngagementClient(String reportingDate);

	@Query(value = "select * from client  , engagement where engagement.id_client = client.id_client and to_char(client.reporting_date,'yyyy-MM-dd')=:daterepo", nativeQuery = true)
	List<Engagement> findByDateReporting(@Param("daterepo") String daterepo);

	// **** CREANCE DOUTEUSE DIRECT BETWEEN TWO DATES ****
	@Query(value = "select g.client.reportingDate as reportingDate, SUM(g.nominalExposure) as sum from engagement g"
			+ " where  g.client.reportingDate <= ?2 and  g.client.reportingDate >= ?1 AND g.glSubhead LIKE '28%' "
			+ " GROUP BY  g.client.reportingDate ORDER BY  g.client.reportingDate ASC")
	List<ISum> creanceDouteuseBetweenTwoDate(Date date_debut, Date date_fin);

	// **** INTERETS RESERVES BETWEEN TWO DATE ****
	@Query(value = "select g.client.reportingDate as reportingDate, SUM(g.intResv) as sum from engagement g where "
			+ "  g.client.reportingDate <= ?2 and g.client.reportingDate >= ?1 "
			+ "GROUP BY g.client.reportingDate ORDER BY g.client.reportingDate ASC ")
	List<ISum> interetsReservesTwoDates(Date date_debut, Date date_fin);

	// **** PROVISIONS BETWEEN TWO DATE ****
	@Query(value = "select g.client.reportingDate as reportingDate, SUM(g.effectiveProvAmt) as sum from engagement g  where"
			+ "  g.client.reportingDate <= ?2 and g.client.reportingDate >= ?1 "
			+ "GROUP BY g.client.reportingDate ORDER BY g.client.reportingDate ASC ")
	List<ISum> provisions(Date date_debut, Date date_fin);

	// **** INTERETS RESERVES CREANCES DOUTEUSES TWO DATES ****
	@Query(value = "select g.client.reportingDate as reportingDate, SUM(g.intResv) from engagement g  where"
			+ "  g.client.reportingDate <= ?2 and g.client.reportingDate >= ?1 AND g.glSubhead LIKE '28%' "
			+ "GROUP BY g.client.reportingDate ORDER BY g.client.reportingDate ASC")
	List<ISum> interetesReservesCreancesDouteuses(Date date_debut, Date date_fin);

	// ------------------------------------------------------------------------------------------------------------------
	// **** CREANCE DOUTEUSE DIRECT ON SPECIFIC DATE ****
	@Query(value = "select SUM(g.nominalExposure) from engagement g, client c  where g.client.obligoreId=c.obligoreId"
			+ " AND c.reportingDate = ?1 AND g.glSubhead LIKE '28%' ")
	Double creanceDouteuseOnSpecificDate(Date date);

}
