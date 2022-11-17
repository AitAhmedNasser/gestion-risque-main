package it.gestionRisque.app.Entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "id_client",referencedColumnName = "ID_CLIENT")
	@JsonBackReference
	private Client client;





}
