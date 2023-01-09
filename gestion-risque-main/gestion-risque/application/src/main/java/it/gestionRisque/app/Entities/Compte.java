package it.gestionRisque.app.Entities;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity (name= "compte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="NUMERO_COMPTE")
	private String accountNumber;
	
	@Column(name="DATE_OUVERTURE")
	private LocalDate openDate;
	
	@Column(name="ISO_CUR_CODE")
	private String isoCurCode;
	
	@Column(name="STATUS_COMPTE")
	private String accountStatus;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "id_client", referencedColumnName = "ID_CLIENT"),
		@JoinColumn(name = "reporting_date", referencedColumnName = "reportingDate") })
	@JsonBackReference
	private Client client;


	// -----Engagement From JSON
		public static Compte compteFromJSON(Map<String, String> data) throws ParseException {
		 String accountNumber = (data.get("NUMERO_COMPTE")!= "" && data.get("NUMERO_COMPTE")!=null)? data.get("NUMERO_COMPTE"):null;
		 String openDate = (data.get("DATE_ENGAGEMENT")!= "" && data.get("DATE_ENGAGEMENT")!=null)? data.get("DATE_ENGAGEMENT"):null;
		 String isoCurCode = (data.get("ISO_CUR_CODE")!= "" && data.get("ISO_CUR_CODE")!=null)? data.get("ISO_CUR_CODE"):null;
		 String accountStatus = (data.get("STATUT_COMPTE")!= "" && data.get("STATUT_COMPTE")!=null)? data.get("STATUT_COMPTE"):null;
		 Client client =  Client.clientFromJSON(data);
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
		 LocalDate openDateToInsert = openDate !=null ? LocalDate.parse(openDate, formatter) : null;
		 
		 Compte compte = new Compte(0L, accountNumber, openDateToInsert, isoCurCode, accountStatus, client);

		 return compte;
		}


}
