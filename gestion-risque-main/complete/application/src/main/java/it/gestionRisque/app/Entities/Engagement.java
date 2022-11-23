package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

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
	
	
	// -----Engagement From JSON
			public static Engagement engagementFromJSON(Map<String, String> data) {
				                                 
				 Boolean facilityId = (data.get("FACILITY_ID")!= "" && data.get("FACILITY_ID")!=null)? Boolean.valueOf( data.get("FACILITY_ID")):null;
				 String facilityType = (data.get("FACILTY_TYPE")!= "" && data.get("FACILTY_TYPE")!=null)? data.get("FACILTY_TYPE"):null;
				 String accountNumber = (data.get("ACCOUNT_NUMBER")!= "" && data.get("ACCOUNT_NUMBER")!=null)? data.get("ACCOUNT_NUMBER"):null;
				 String mainProductDesc = (data.get("MAIN_PRODUC_DESC")!= "" && data.get("MAIN_PRODUC_DESC")!=null)? data.get("MAIN_PRODUC_DESC"):null;
				 String productDescription =  (data.get("PRODUCT_CODE_DESC")!= "" && data.get("PRODUCT_CODE_DESC")!=null)? data.get("PRODUCT_CODE_DESC"):null;
				 String productCode = (data.get("PRODUCT_CODE")!= "" && data.get("PRODUCT_CODE")!=null)? data.get("PRODUCT_CODE"):null;
				 Double nominalExposure = (data.get("NOMINAL_EXPOSURE")!= "" && data.get("NOMINAL_EXPOSURE")!=null)? Double.parseDouble(data.get("NOMINAL_EXPOSURE")):null;
				 Double effectiveProvAmt = (data.get("EFFECTIVE_PROV_AMT")!= "" && data.get("EFFECTIVE_PROV_AMT")!=null)? Double.parseDouble(data.get("EFFECTIVE_PROV_AMT")):null;
				 Double intResv = (data.get("INT_RESV")!= "" && data.get("INT_RESV")!=null)? Double.parseDouble(data.get("INT_RESV")):null; 
				 Double pca = (data.get("PCA")!= "" && data.get("PCA")!=null)? Double.parseDouble(data.get("PCA")):null; 
				 Double part_20700_20710 = (data.get("PART_20700_20710")!= "" && data.get("PART_20700_20710")!=null)? Double.parseDouble(data.get("PART_20700_20710")):null;
				 Double islamic = (data.get("ISLAMIC")!= "" && data.get("ISLAMIC")!=null)? Double.parseDouble(data.get("ISLAMIC")):null;
				 Double leasing = (data.get("LEASING")!= "" && data.get("LEASING")!=null)? Double.parseDouble(data.get("LEASING")):null;
				 String maturityDate = (data.get("MATURITY_DATE")!= "" && data.get("MATURITY_DATE")!=null)? data.get("MATURITY_DATE"):null;
				 Double maturityDays = (data.get("MATURITY_DAYS")!= "" && data.get("MATURITY_DAYS")!=null)? Double.parseDouble(data.get("MATURITY_DAYS")):null;
				 String lcNumber = (data.get("IC_NUMBER")!= "" && data.get("IC_NUMBER")!=null)? data.get("IC_NUMBER"):null;
				 Double ifrsBalance = (data.get("IFRS_BALANCE")!= "" && data.get("IFRS_BALANCE")!=null)? Double.parseDouble(data.get("IFRS_BALANCE")):null;
				 String installmentPayementCycle = (data.get("INSTALLEMENT_PAYEMENT_CYCLE")!= "" && data.get("INSTALLEMENT_PAYEMENT_CYCLE")!=null)? data.get("INSTALLEMENT_PAYEMENT_CYCLE"):null;
				 String isoCurCode = (data.get("ISO_CUR_CODE")!= "" && data.get("ISO_CUR_CODE")!=null)? data.get("ISO_CUR_CODE"):null;
				 Float ageOfLoan = (data.get("AGE_OF_LOAN")!= "" && data.get("AGE_OF_LOAN")!=null)?Float.parseFloat(data.get("AGE_OF_LOAN")) :null;
				 String firstInstal = (data.get("FIRST_INSTAL")!= "" && data.get("FIRST_INSTAL")!=null)? data.get("FIRST_INSTAL"):null;
				 String loanCurrency = (data.get("LOAN_CURRENCY")!= "" && data.get("LOAN_CURRENCY")!=null)? data.get("LOAN_CURRENCY"):null;
				 Double nominalInterestRate = (data.get("NOMINAL_INTEREST_RATE")!= "" && data.get("NOMINAL_INTEREST_RATE")!=null)? Double.parseDouble(data.get("NOMINAL_INTEREST_RATE")):null;
				 Double limite = (data.get("LIMIT")!= "" && data.get("LIMIT")!=null)? Double.parseDouble(data.get("LIMIT")):null;
				 String glSubhead = (data.get("GL_SUBHEAD")!= "" && data.get("GL_SUBHEAD")!=null)? data.get("GL_SUBHEAD"):null;
				 String glDescription = (data.get("GL_DESCRIPTION")!= "" && data.get("GL_DESCRIPTION")!=null)? data.get("GL_DESCRIPTION"):null;
				 //impaye
				 Double impayes = 0.0;//default value 
				 String defaultDate = (data.get("DEFAULT_DATE")!= "" && data.get("DEFAULT_DATE")!=null)? data.get("DEFAULT_DATE"):null;
				 Double dpd = (data.get("DPD")!= "" && data.get("DPD")!=null)? Double.parseDouble(data.get("DPD")):null;
				 String restructuredDate = (data.get("RESTRUCTURED_DATE")!= "" && data.get("RESTRUCTURED_DATE")!=null)? data.get("RESTRUCTURED_DATE"):null;
				 String reasonForNpa =  (data.get("REASON_FOR_NPA")!= "" && data.get("REASON_FOR_NPA")!=null)? data.get("REASON_FOR_NPA"):null;
				 String reasonForNpaDesc = (data.get("REASON_FOR_NPA_DESC")!= "" && data.get("REASON_FOR_NPA_DESC")!=null)? data.get("REASON_FOR_NPA_DESC"):null;
				 //Garantie
				 Double realEstateColValue = (data.get("REAL_ESTATE_COL_VALUE")!= "" && data.get("REAL_ESTATE_COL_VALUE")!=null)? Double.parseDouble(data.get("REAL_ESTATE_COL_VALUE")):null;
				 String currencyRealEstateColValue = (data.get("CURRENCY_REAL_ESTATE_COL_VALUE")!= "" && data.get("CURRENCY_REAL_ESTATE_COL_VALUE")!=null)? data.get("CURRENCY_REAL_ESTATE_COL_VALUE"):null;
				 Double valeurexpertise = (data.get("VALEUR_EXPERTISE")!= "" && data.get("VALEUR_EXPERTISE")!=null)? Double.parseDouble(data.get("VALEUR_EXPERTISE")):null;
				 String currencyValeur_expertise = (data.get("CURRENCY_VALEUR_EXPERTISE")!= "" && data.get("CURRENCY_VALEUR_EXPERTISE")!=null)? data.get("CURRENCY_VALEUR_EXPERTISE"):null;
				 Double cashColValue = (data.get("CASH_COL_VALUE")!= "" && data.get("CASH_COL_VALUE")!=null)? Double.parseDouble(data.get("CASH_COL_VALUE")):null;
				 String currencyCashColValue = (data.get("CURRENCY_CASH_COL_VALUE")!= "" && data.get("CURRENCY_CASH_COL_VALUE")!=null)? data.get("CURRENCY_CASH_COL_VALUE"):null;
				 Double depbdzd = (data.get("depbdzd")!= "" && data.get("depbdzd")!=null)? Double.parseDouble(data.get("depbdzd")):null;
				 Double depbdevise = (data.get("depbdevise")!= "" && data.get("depbdevise")!=null)? Double.parseDouble(data.get("depbdevise")):null;
				 String currencyDepbDevise = (data.get("currencyDepbDevise")!= "" && data.get("currencyDepbDevise")!=null)? data.get("currencyDepbDevise"):null;
				 Double depconf = (data.get("depconf")!= "" && data.get("depconf")!=null)? Double.parseDouble(data.get("depconf")):null;
				 String shareColValue = (data.get("SHARE_COL_VALUE")!= "" && data.get("SHARE_COL_VALUE")!=null)? data.get("SHARE_COL_VALUE"):null;
				 Double veicleColValue = (data.get("VEICLE_COL_VALUE")!= "" && data.get("VEICLE_COL_VALUE")!=null)? Double.parseDouble(data.get("VEICLE_COL_VALUE")):null;
				 String currencyVehicleColValue = (data.get("CURRENCY_VEICLE_COL_VALUE")!= "" && data.get("CURRENCY_VEICLE_COL_VALUE")!=null)? data.get("CURRENCY_VEICLE_COL_VALUE"):null;
				 Double valeurVehicule = (data.get("VALEUR_VEHICULE")!= "" && data.get("VALEUR_VEHICULE")!=null)? Double.parseDouble(data.get("VALEUR_VEHICULE")):null;
				 String currencyValeurVehicule = (data.get("CURRENCY_VALEUR_VEHICULE")!= "" && data.get("CURRENCY_VALEUR_VEHICULE")!=null)? data.get("CURRENCY_VALEUR_VEHICULE"):null;
				 Double bankingLgColValue = (data.get("BANKING_LG_COL_VALUE")!= "" && data.get("BANKING_LG_COL_VALUE")!=null)? Double.parseDouble(data.get("BANKING_LG_COL_VALUE")):null;
				 String currencyBankingLgColValue = (data.get("CURRENCY_BANKING_LG_COL_VALUE")!= "" && data.get("CURRENCY_BANKING_LG_COL_VALUE")!=null)? data.get("CURRENCY_BANKING_LG_COL_VALUE"):null;
				
				Client client =  Client.clientFromJSON(data);
				
				// converting string to date
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate maturityDateToInsert = maturityDate != null ? LocalDate.parse(maturityDate,formatter):null;
				LocalDate firstInstalToInsert = firstInstal != null ? LocalDate.parse(firstInstal,formatter):null; 
				LocalDate defaultDateToInsert = defaultDate != null ? LocalDate.parse(defaultDate,formatter):null; 
				LocalDate restructuredDateToInsert = restructuredDate !=null ? LocalDate.parse(restructuredDate,formatter):null; 
							 
				 Engagement engagement = new Engagement(
						 0L, facilityId, facilityType, accountNumber, mainProductDesc,productDescription,
						 productCode, nominalExposure, effectiveProvAmt, intResv, pca, part_20700_20710, islamic,
						 leasing, maturityDateToInsert, maturityDays, lcNumber, ifrsBalance, installmentPayementCycle, 
						 isoCurCode, ageOfLoan, firstInstalToInsert, loanCurrency, nominalInterestRate, limite, glSubhead, glDescription,
						 impayes, defaultDateToInsert, dpd, restructuredDateToInsert, reasonForNpa,
		 				 reasonForNpaDesc, realEstateColValue, currencyRealEstateColValue, valeurexpertise, 
		 				 currencyValeur_expertise, cashColValue, currencyCashColValue,depbdzd,
		 				 depbdevise,currencyDepbDevise, depconf, shareColValue, veicleColValue, currencyVehicleColValue,
		 				 valeurVehicule, currencyValeurVehicule, bankingLgColValue, currencyBankingLgColValue,
		 				 client
						 );
				 
				 System.out.println(engagement);
				
				 return engagement;
					
				}
}
	