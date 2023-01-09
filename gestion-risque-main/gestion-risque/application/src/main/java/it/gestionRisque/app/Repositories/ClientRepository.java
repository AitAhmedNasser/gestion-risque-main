package it.gestionRisque.app.Repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.portefeuil.ISum;
import it.gestionRisque.app.portefeuil.PortefeuilDirectDTO;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

	Optional<Client> findByObligoreIdAndReportingDate(String obligoreId, String reportingDate);

	@Query(value = "select * from client  , engagement where engagement.id_client = client.id and to_char(client.reporting_date,'yyyy-MM-dd')=:daterepo", nativeQuery = true)
	List<Client> findByDateReporting(@Param("daterepo") String daterepo);

	// select * from client where
	// to_char(client.reporting_date,'yyyy-MM-dd')>='2019-12-31' and EXTRACT(YEAR
	// from client.reporting_date )<=2020
	@Query(value = "select client.reporting_date from client where  EXTRACT(YEAR from client.reporting_date ) = :year group by client.reporting_date", nativeQuery = true)
	List<String> findByReportingDate(@Param("year") Integer reportingDate);

	// entre deux date
	@Query(value = "select client.reporting_date from client where   to_char(client.reporting_date,'yyyy-MM-dd')>= :lastdatereport or   \n"
			+ "	EXTRACT(YEAR from client.reporting_date )<= :year group by client.reporting_date ", nativeQuery = true)
	List<String> findBetweenTowDate(@Param("lastdatereport") String lastdatereport,
			@Param("year") Integer reportingDateyear);
	
	// **** testing ****
	@Query(value = "select reportingDate as reportingDate, SUM(soldeBalance) as sum from client where reportingDate <= ?2 and reportingDate >= ?1 GROUP BY reportingDate ORDER BY reportingDate ASC")
	List<ISum> sumSolde_balanceByReporting_date(Date date_debut, Date date_fin);

	// **** PORTEFEUILLE DIRECT BETWEEN TWO DATES ****
	@Query(value = "select reportingDate as reportingDate, SUM(soldeBalance) as sum from client where reportingDate <= ?2 and reporting_date >= ?1 GROUP BY reportingDate ORDER BY reportingDate ASC")
	List<ISum> soldeBalanceBetweenTwoDate(Date date_debut, Date date_fin);

	// **** PORTEFEUILLE DIRECT ON SPECIFIC DATE ****
	@Query(value = "select SUM(soldeBalance) from client where reportingDate = ?1")
	Double soldeBalanceOnSpecificDate(Date date);


	@Query( value= "select b.desc_secteur,sum(b.anneePrec) anneePrec, sum(b.annee) annee from(\r\n"
			+ "select (case\r\n"
			+ "when a.code_secteur in ('4','3') then 'INDUSTRIES' \r\n"
			+ "when a.code_secteur in ('9') then 'TRANSPORTS, ET COMMUNICATIONS'\r\n"
			+ "when a.code_secteur in ('7') then 'REPARATION AUTOMOBILES ET D''ARTICLES DOMESTIQUES'\r\n"
			+ "when a.code_secteur in ('11') then 'IMMOBILIER, LOCATION ET SERVICES AUX ENTREPRISES'\r\n"
			+ "when a.code_secteur in ('14') then 'SANTE ET ACTION SOCIALE'\r\n"
			+ "when a.code_secteur in ('6') then 'CONSTRUCTION'\r\n"
			+ "when a.code_secteur in ('15') then 'SERVICES COLLECTIFS SOCIAUX ET PERSONNELS'\r\n"
			+ "when a.code_secteur in ('16') then 'SERVICES DOMESTIQUES'\r\n"
			+ "when a.code_secteur in ('8') then 'HOTELS ET RESTAURANTS'\r\n"
			+ "else  'PARTICULIERS'\r\n"
			+ "end) as desc_secteur, sum(a.annee) annee, sum(a.anneePrec) anneePrec from(\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, code_secteur, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ "union\r\n"
			+ "SELECT count(id_client) annee, 0 anneePrec, code_secteur, desc_secteur FROM public.client\r\n"
			+ "where client.reporting_date=to_date(:daterepo,'yyyy-MM-dd')-365\r\n"
			+ "group by code_secteur, desc_secteur, reporting_date\r\n"
			+ ")	a\r\n"
			+ "	group by a.code_secteur\r\n"
			+ ") b	\r\n"
			+ " group by 1 order by 1"	,nativeQuery = true)	
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
    
    
    @Query(value="select coalesce(count(c.id_client),cast(0 as bigint)) nbr_client,\r\n"
    		+ "			 (case\r\n"
    		+ "			when c.desc_wilaya in ('ALGER','BOUMERDES','BLIDA','TIPAZA') then 'CENTRE' \r\n"
    		+ "			when c.desc_wilaya  in ('CONSTANTINE','SETIF','BEJAIA','MILA','OUM ELBOUAGHI','BOUIRA','BISKRA') then 'EST'\r\n"
    		+ "			when c.desc_wilaya  in ('MEDEA','ORAN', 'CHLEF') then 'OUEST'\r\n"
    		+ "         when c.desc_wilaya  is null then 'AUTRES'"
    		+ "			end) as zone ,(case"
    		+ "    					when c.desc_wilaya in ('ALGER','BOUMERDES','BLIDA','TIPAZA') then 1 \r\n"
    		+ "    					when c.desc_wilaya  in ('CONSTANTINE','SETIF','BEJAIA','MILA','OUM ELBOUAGHI','BOUIRA','BISKRA') then 2\r\n"
    		+ "    					when c.desc_wilaya  in ('MEDEA','ORAN', 'CHLEF') then 3\r\n"
    		+ "    		        when c.desc_wilaya  is null then 4\r\n"
    		+ "    					end) ordre "
    		+ "from client c	\r\n"
    		+ "where c.reporting_date=to_date(:daterepo,'yyyy-MM-dd') \r\n"
    		+ "group by zone,ordre order by ordre",nativeQuery = true)
    List<String[]> findGroupedByZone(@Param("daterepo")String daterepo);
    
    @Query(value="select sum(a.nbr_client) from (\r\n"
    		+ "select count(c.id_client) nbr_client,\r\n"
    		+ "			 (case\r\n"
    		+ "			when c.desc_wilaya in ('ALGER','BOUMERDES','BLIDA','TIPAZA') then 'CENTRE' \r\n"
    		+ "			when c.desc_wilaya  in ('CONSTANTINE','SETIF','BEJAIA','MILA','OUM ELBOUAGHI','BOUIRA','BISKRA') then 'EST'\r\n"
    		+ "			when c.desc_wilaya  in ('MEDEA','ORAN', 'CHLEF') then 'OUEST'\r\n"
    		+ "         when c.desc_wilaya  is null then 'AUTRES'"
    		+ "			end) as zone \r\n"
    		+ "from client c	\r\n"
    		+ "where c.reporting_date=to_date(:daterepo,'yyyy-MM-dd')"
    		+ "group by zone) a",nativeQuery = true)
    List<String[]> findTotalGroupedByZone(@Param("daterepo")String daterepo);

}
