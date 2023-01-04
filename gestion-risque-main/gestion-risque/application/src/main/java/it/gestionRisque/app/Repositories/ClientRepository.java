package it.gestionRisque.app.Repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Client;


@RepositoryRestResource
public interface ClientRepository  extends JpaRepository<Client, Long>{

Optional<Client> findByObligoreIdAndReportingDate (String obligoreId, String reportingDate );

	@Query( value= "select * from client  , engagement where engagement.id_client = client.id and to_char(client.reporting_date,'yyyy-MM-dd')=:daterepo",nativeQuery = true)	
 List<Client> findByDateReporting (@Param("daterepo")String daterepo);
 
 // 	select * from client where   to_char(client.reporting_date,'yyyy-MM-dd')>='2019-12-31' and   EXTRACT(YEAR from client.reporting_date )<=2020  
	@Query(value = "select client.reporting_date from client where  EXTRACT(YEAR from client.reporting_date ) = :year group by client.reporting_date",nativeQuery = true)
	List<String> findByReportingDate( @Param("year") Integer reportingDate);
	
	// entre deux date 
	@Query(value = "select  client.reporting_date from client where   to_char(client.reporting_date,'yyyy-MM-dd')>= :lastdatereport or   \n"
			+ "	EXTRACT(YEAR from client.reporting_date )<= :year group by client.reporting_date ",nativeQuery = true)
	List<String> findBetweenTowDate(@Param("lastdatereport") String lastdatereport , @Param("year") Integer reportingDateyear);

	@Query( value= "select a.desc_secteur, a.anneePrec, a.annee from(\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ "\r\n"
			+ "union\r\n"
			+ "\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')-365\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ ") a",nativeQuery = true)	
    List<String[]> findGroupedBySecteur(@Param("daterepo")String daterepo);
    
    @Query( value= "select cast(sum(a.anneePrec) as character varying) as anneePrec, cast(sum(a.annee) as character varying) as annee from(\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ "\r\n"
			+ "union\r\n"
			+ "\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')-365\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ ") a",nativeQuery = true)	
    List<String[]> findTotalGroupedBySecteur(@Param("daterepo")String daterepo);
}
