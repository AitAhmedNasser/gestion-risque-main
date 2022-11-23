package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonBackReference;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
@Entity(name = "engagement")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engagement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
   
	@Column(nullable = true, name="FACILITY_ID")
	private Boolean facilityId;
	
	@Column(nullable = true, name="TYPE_ENGAGEMENT")
	private String facilityType;
	
	@Column(nullable = true, name="NUMERO_COMPTE")
	private String accountNumber;
	
	@Column(nullable = true, name="MAIN_PROD_DESC")
	private String mainProductDesc;
	
	@Column(nullable = true, name="PRODUCT_DESCRIPTION")
	private String productDescription;
	
	@Column(nullable = true, name="PRODUCT_CODE")
	private String producCode;
	
	@Column(nullable = true, name="NOMINAL_EXPOSURE")
	private Double nominalExposure;
	
	@Column(nullable = true, name="PROVISIONS")
	private Double effectiveProvAmt;
	
	@Column(nullable = true, name="INT_RESERVES")
	private Double intResv;
	
	@Column(nullable = true, name="INT_A_PERC_CREDIT_AMORTISSABLES")
	private Double pca;	
	
	@Column(nullable = true, name="INT_A_PERC_CREDIT_FINANCEMENT")
	private Double part_20700_20710;
	
	@Column(nullable = true, name="MARGE_ISLAMIQUE")
	private Double islamic;
	
	@Column(nullable = true, name="MARGE_LEASING")
	private Double leasing;
	
	@Column(nullable = true, name="DATE_MATURITE")
	private LocalDate maturityDate;
	
	@Column(nullable = true, name="JOURS_MATURITE")
	private Double maturityDays;
	
	@Column(nullable = true, name="NUMERO_LC")
	private String lcNumber;
	
	@Column(nullable = true, name="IFRS_BALANCE")
	private Double ifrsBalance;
	
	@Column(nullable = true, name="CYCLE_DE_PAYEMENT")
	private String installmentPayementCycle;
	
	@Column(nullable = true, name="ISO_CUR_CODE")
	private String isoCurCode;
	
	@Column(nullable = true, name="DUREE_ENGAGEMENT")
	private Float ageOfLoan;
	
	@Column(nullable = true, name="DATE_1ER_ECHEANCE")
	private LocalDate firstInstal;
	
	@Column(nullable = true, name="LOAN_CURRENCY")
	private String loanCurrency;
	
	@Column(nullable = true, name="NOMINAL_INTEREST_RATE")
	private Double nominalInterestRate;
	
	@Column(nullable = true, name="LIMITE")
	private Double limite;
	
	@Column(nullable = true, name="GL_SUBHEAD")
	private String glSubhead;
	
	@Column(nullable = true, name="GL_DESCRIPTION")
	private String glDescription;
	
	// Impaye
	@Column(nullable = true, name = "IMPAYES")
	private Double impaye;
	
	@Column(nullable = true )
	private LocalDate defaultDate;
	
	@Column(nullable = true, name="DPD")
	private Double dpd;
	
	@Column(nullable = true)
	private LocalDate restructuredDate;
	

	
	@Column(nullable = true)
	private String reasonForNpa;
	
	@Column(nullable = true)
	private String reasonForNpaDesc;
	
	//Garantie
	@Column(nullable = true, name="VALEUR_HYPOTHEQUE")
	private Double realEstateColValue;
	
	@Column(nullable = true, name="DEVISE_HYPOTHEQUE")
	private String currencyRealEstateColValue;
	
	@Column(nullable = true, name="VALEUR_BIEN")
	private Double valeurexpertise;
	
	@Column(nullable = true, name="DEVISE_BIEN")
	private String currencyValeur_expertise;
	
	@Column(nullable = true, name="VALEUR_CASH")
	private Double cashColValue;
	
	@Column(nullable = true, name="DEVISE")
	private String currencyCashColValue;
	
	@Column(nullable = true, name="DEPOT_DENIARS")
	private Double depbdzd;
	
	@Column(nullable = true, name="DEPOT_DEVISE")
	private Double depbdevise;
	
	@Column(nullable = true, name="DEVISE_DEPOTDEVISE")
	private String currencyDepbDevise;
	
	@Column(nullable = true, name="DEPOTCONF")
	private Double depconf;
	
	@Column(nullable = true)
	private String shareColValue;
	
	@Column(nullable = true, name="VEICLES")
	private Double veicleColValue;
	
	@Column(nullable = true, name="CURRENY_VEILCLE_COL_VALUE")
	private String currencyVehicleColValue;
	
	@Column(nullable = true, name="VALEUR_VEHICULE")
	private Double  valeurVehicule;
	
	@Column(nullable = true, name="CURRENCY_VALEUR_VEHICULE")
	private String currencyValeurVehicule;
	
	@Column(nullable = true, name="GARANTIES_BANQUES")
	private Double bankingLgColValue;
	
	@Column(nullable = true, name="DEVISE_GARANTIES_BANQUES")
	private String currencyBankingLgColValue;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_client",referencedColumnName = "ID_CLIENT")
	@JsonBackReference
	private Client client;
}
	