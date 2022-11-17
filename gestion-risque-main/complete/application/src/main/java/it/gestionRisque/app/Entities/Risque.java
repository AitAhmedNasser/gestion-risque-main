package it.gestionRisque.app.Entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
@Entity
@Data
@AllArgsConstructor
public class Risque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id ;
private double riskRate;
private Date finacialStatementDate;
private double indicateurDefaillance;

/*
 * @OneToOne (mappedBy = "risque" ,cascade = CascadeType.ALL,fetch =
 * FetchType.LAZY) private Client client;
 */
}
