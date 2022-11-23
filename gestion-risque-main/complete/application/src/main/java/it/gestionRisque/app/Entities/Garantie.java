package it.gestionRisque.app.Entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Garantie {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	private Double realEstateColValue;
	private String currencyRealEstateColValue;
	private Double valeurexpertise;
	private String currencyValeur_expertise;
	private Double cashColValue;
	private String currencyCashColValue;
	private Double depbdzd;
	private Double depbdevise;
	private String currencyDepbDevise;
	private Double depconf;
	private Double shareColValue;
	private Double veicleColValue;
	private String currencyVehicleColValue;
	private Double  valeurVehicule;
	private String currencyValeurVehicule;
	private Double bankingLgColValue;
	private String currencyBankingLgColValue;




}
