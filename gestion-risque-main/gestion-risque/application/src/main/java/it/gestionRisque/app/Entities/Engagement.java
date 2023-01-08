package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.poi.ss.usermodel.DateUtil;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity(name = "engagement")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Engagement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, name = "FACILITY_ID")
	private Boolean facilityId;

	@Column(nullable = true, name = "TYPE_ENGAGEMENT")
	private String facilityType;

	@Column(nullable = true, name = "NUMERO_COMPTE")
	private String accountNumber;

	@Column(nullable = true, name = "MAIN_PROD_DESC")
	private String mainProductDesc;

	@Column(nullable = true, name = "PRODUCT_DESCRIPTION")
	private String productDescription;

	@Column(nullable = true, name = "PRODUCT_CODE")
	private String producCode;

	@Column(nullable = true, name = "NOMINAL_EXPOSURE")
	private Double nominalExposure;

	@Column(nullable = true, name = "PROVISIONS")
	private Double effectiveProvAmt;

	@Column(nullable = true, name = "INT_RESERVES")
	private Double intResv;

	@Column(nullable = true, name = "INT_A_PERC_CREDIT_AMORTISSABLES")
	private Double pca;

	@Column(nullable = true, name = "INT_A_PERC_CREDIT_FINANCEMENT")
	private Double part_20700_20710;

	@Column(nullable = true, name = "MARGE_ISLAMIQUE")
	private Double islamic;

	@Column(nullable = true, name = "MARGE_LEASING")
	private Double leasing;

	@Column(nullable = true, name = "DATE_MATURITE")
	private LocalDate maturityDate;

	@Column(nullable = true, name = "JOURS_MATURITE")
	private Double maturityDays;

	@Column(nullable = true, name = "NUMERO_LC")
	private String lcNumber;

	@Column(nullable = true, name = "IFRS_BALANCE")
	private Double ifrsBalance;

	@Column(nullable = true, name = "CYCLE_DE_PAYEMENT")
	private String installmentPayementCycle;

	@Column(nullable = true, name = "ISO_CUR_CODE")
	private String isoCurCode;

	@Column(nullable = true, name = "DUREE_ENGAGEMENT")
	private Float ageOfLoan;

	@Column(nullable = true, name = "DATE_1ER_ECHEANCE")
	private LocalDate firstInstal;

	@Column(nullable = true, name = "LOAN_CURRENCY")
	private String loanCurrency;

	@Column(nullable = true, name = "NOMINAL_INTEREST_RATE")
	private Double nominalInterestRate;

	@Column(nullable = true, name = "LIMITE")
	private Double limite;

	@Column(nullable = true, name = "GL_SUBHEAD")
	private String glSubhead;

	@Column(nullable = true, name = "GL_DESCRIPTION")
	private String glDescription;

	// Impaye
	@Column(nullable = true, name = "IMPAYES")
	private Double impaye;

	@Column(nullable = true)
	private LocalDate defaultDate;

	@Column(nullable = true, name = "DPD")
	private Double dpd;

	@Column(nullable = true)
	private LocalDate restructuredDate;

	@Column(nullable = true)
	private String reasonForNpa;

	@Column(nullable = true)
	private String reasonForNpaDesc;

	// Garantie
	@Column(nullable = true, name = "VALEUR_HYPOTHEQUE")
	private Double realEstateColValue;

	@Column(nullable = true, name = "DEVISE_HYPOTHEQUE")
	private String currencyRealEstateColValue;

	@Column(nullable = true, name = "VALEUR_BIEN")
	private Double valeurexpertise;

	@Column(nullable = true, name = "DEVISE_BIEN")
	private String currencyValeur_expertise;

	@Column(nullable = true, name = "VALEUR_CASH")
	private Double cashColValue;

	@Column(nullable = true, name = "DEVISE")
	private String currencyCashColValue;

	@Column(nullable = true, name = "DEPOT_DENIARS")
	private Double depbdzd;

	@Column(nullable = true, name = "DEPOT_DEVISE")
	private Double depbdevise;

	@Column(nullable = true, name = "DEVISE_DEPOTDEVISE")
	private String currencyDepbDevise;

	@Column(nullable = true, name = "DEPOTCONF")
	private Double depconf;

	@Column(nullable = true)
	private String shareColValue;

	@Column(nullable = true, name = "VEICLES")
	private Double veicleColValue;

	@Column(nullable = true, name = "CURRENY_VEILCLE_COL_VALUE")
	private String currencyVehicleColValue;

	@Column(nullable = true, name = "VALEUR_VEHICULE")
	private Double valeurVehicule;

	@Column(nullable = true, name = "CURRENCY_VALEUR_VEHICULE")
	private String currencyValeurVehicule;

	@Column(nullable = true, name = "GARANTIES_BANQUES")
	private Double bankingLgColValue;

	@Column(nullable = true, name = "DEVISE_GARANTIES_BANQUES")
	private String currencyBankingLgColValue;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "id_client", referencedColumnName = "ID_CLIENT"),
			@JoinColumn(name = "reporting_date", referencedColumnName = "reportingDate") })
	@JsonBackReference
	private Client client;

	// -----Engagement From JSON

	public static Engagement engagementFromJSON(Map<String, String> data) throws ParseException {

		Boolean facilityId = (data.get("FACILITY_ID") != "" && data.get("FACILITY_ID") != null)
				? Boolean.valueOf(data.get("FACILITY_ID"))
				: null;
		String facilityType = (data.get("TYPE_ENGAGEMENT") != "" && data.get("TYPE_ENGAGEMENT") != null)
				? data.get("TYPE_ENGAGEMENT")
				: null;
		String accountNumber = (data.get("NUMERO_COMPTE") != "" && data.get("NUMERO_COMPTE") != null)
				? data.get("NUMERO_COMPTE")
				: null;
		String mainProductDesc = (data.get("CODE_PRODUIT_PRINCIPAL") != ""
				&& data.get("CODE_PRODUIT_PRINCIPAL") != null) ? data.get("CODE_PRODUIT_PRINCIPAL") : null;
		String productDescription = (data.get("DESC_SOUS_PRODUIT") != "" && data.get("DESC_SOUS_PRODUIT") != null)
				? data.get("DESC_SOUS_PRODUIT")
				: null;
		String productCode = (data.get("CODE_SOUS_PRODUIT") != "" && data.get("CODE_SOUS_PRODUIT") != null)
				? data.get("CODE_SOUS_PRODUIT")
				: null;
		Double nominalExposure = (data.get("NOMINAL_EXPOSURE") != "" && data.get("NOMINAL_EXPOSURE") != null)
				? Double.parseDouble(data.get("NOMINAL_EXPOSURE"))
				: null;
		Double effectiveProvAmt = (data.get("PROVISIONS") != "" && data.get("PROVISIONS") != null)
				? Double.parseDouble(data.get("PROVISIONS"))
				: null;
		Double intResv = (data.get("INT_RESERVES") != "" && data.get("INT_RESERVES") != null)
				? Double.parseDouble(data.get("INT_RESERVES"))
				: null;
		Double pca = (data.get("INTERETS A PERCEVOIS CREDITS FINANCEMENT") != ""
				&& data.get("INTERETS A PERCEVOIS CREDITS FINANCEMENT") != null)
						? Double.parseDouble(data.get("INTERETS A PERCEVOIS CREDITS FINANCEMENT"))
						: null;
		Double part_20700_20710 = (data.get("INTERETS A PERCEVOIS CREDITS AMORTISSABLES") != ""
				&& data.get("INTERETS A PERCEVOIS CREDITS AMORTISSABLES") != null)
						? Double.parseDouble(data.get("INTERETS A PERCEVOIS CREDITS AMORTISSABLES"))
						: null;
		Double islamic = (data.get("ISLAMIC") != "" && data.get("ISLAMIC") != null)
				? Double.parseDouble(data.get("ISLAMIC"))
				: null;
		Double leasing = (data.get("MARGE_LEASING") != "" && data.get("MARGE_LEASING") != null)
				? Double.parseDouble(data.get("MARGE_LEASING"))
				: null;
		String maturityDate = (data.get("DATE_MATURITE") != "" && data.get("DATE_MATURITE") != null)
				? data.get("DATE_MATURITE")
				: null;
		Double maturityDays = (data.get("JOURS_MATURITE") != "" && data.get("JOURS_MATURITE") != null)
				? Double.parseDouble(data.get("JOURS_MATURITE"))
				: null;
		String lcNumber = (data.get("IC_NUMBER") != "" && data.get("IC_NUMBER") != null) ? data.get("IC_NUMBER") : null;
		Double ifrsBalance = (data.get("IFRS_BALANCE") != "" && data.get("IFRS_BALANCE") != null)
				? Double.parseDouble(data.get("IFRS_BALANCE"))
				: null;
		String installmentPayementCycle = (data.get("CYCLE DE PAYEMENT") != "" && data.get("CYCLE DE PAYEMENT") != null)
				? data.get("CYCLE DE PAYEMENT")
				: null;
		String isoCurCode = (data.get("ISO_CUR_CODE") != "" && data.get("ISO_CUR_CODE") != null)
				? data.get("ISO_CUR_CODE")
				: null;
		Float ageOfLoan = (data.get("DUREE_ENGAGEMENT") != "" && data.get("DUREE_ENGAGEMENT") != null)
				? Float.parseFloat(data.get("DUREE_ENGAGEMENT"))
				: null;
		String firstInstal = (data.get("DATE_1ERE_ECHEANCE") != "" && data.get("DATE_1ERE_ECHEANCE") != null)
				? data.get("DATE_1ERE_ECHEANCE")
				: null;
		String loanCurrency = (data.get("DEVISES_ENGAGEMENT") != "" && data.get("DEVISES_ENGAGEMENT") != null)
				? data.get("DEVISES_ENGAGEMENT")
				: null;
		Double nominalInterestRate = (data.get("TAUX INTERET") != "" && data.get("TAUX INTERET") != null)
				? Double.parseDouble(data.get("TAUX INTERET"))
				: null;
		Double limite = (data.get("LIMITE") != "" && data.get("LIMITE") != null)
				? Double.parseDouble(data.get("LIMITE"))
				: null;
		String glSubhead = (data.get("CHAPITRE") != "" && data.get("CHAPITRE") != null) ? data.get("CHAPITRE") : null;
		String glDescription = (data.get("DESCRIPTION_CHAPITRE") != "" && data.get("DESCRIPTION_CHAPITRE") != null)
				? data.get("DESCRIPTION_CHAPITRE")
				: null;
		// impaye
		Double impayes = 0.0;// default value
		String defaultDate = (data.get("DATE_1er_IMPAYE") != null && !data.get("DATE_1er_IMPAYE").isEmpty())
				? (data.get("DATE_1er_IMPAYE"))
				: null;
		Double dpd = (data.get("DPD") != null && !data.get("DPD").isEmpty()) ? Double.parseDouble(data.get("DPD"))
				: null;
		String restructuredDate = (data.get("RESTRUCTURATION_DATE") != "" && data.get("RESTRUCTURATION_DATE") != null)
				? data.get("RESTRUCTURATION_DATE")
				: null;
		String reasonForNpa = (data.get("CODE_REASON_CLASSEMENT") != "" && data.get("CODE_REASON_CLASSEMENT") != null)
				? data.get("CODE_REASON_CLASSEMENT")
				: null;
		String reasonForNpaDesc = (data.get("DESC _REASON_CLASSEMENT") != ""
				&& data.get("DESC _REASON_CLASSEMENT") != null) ? data.get("DESC _REASON_CLASSEMENT") : null;
		// Garantie
		Double realEstateColValue = (data.get("VALEUR_HYPOTHEQUE") != "" && data.get("VALEUR_HYPOTHEQUE") != null)
				? Double.parseDouble(data.get("VALEUR_HYPOTHEQUE"))
				: null;
		String currencyRealEstateColValue = (data.get("CURRENCY_REAL_ESTATE_COL_VALUE") != ""
				&& data.get("CURRENCY_REAL_ESTATE_COL_VALUE") != null) ? data.get("CURRENCY_REAL_ESTATE_COL_VALUE")
						: null;
		Double valeurexpertise = (data.get("VALEUR_EXPERTISE") != "" && data.get("VALEUR_EXPERTISE") != null)
				? Double.parseDouble(data.get("VALEUR_EXPERTISE"))
				: null;
		String currencyValeur_expertise = (data.get("CURRENCY_VALEUR_EXPERTISE") != ""
				&& data.get("CURRENCY_VALEUR_EXPERTISE") != null) ? data.get("CURRENCY_VALEUR_EXPERTISE") : null;
		Double cashColValue = (data.get("VALEUR_CASH") != "" && data.get("VALEUR_CASH") != null)
				? Double.parseDouble(data.get("VALEUR_CASH"))
				: null;
		String currencyCashColValue = (data.get("DEVISE_CASH") != "" && data.get("DEVISE_CASH") != null)
				? data.get("DEVISE_CASH")
				: null;
		Double depbdzd = (data.get("DEPOTSDINARS") != "" && data.get("DEPOTSDINARS") != null)
				? Double.parseDouble(data.get("DEPOTSDINARS"))
				: null;
		Double depbdevise = (data.get("DEPOTSDEVISES") != "" && data.get("DEPOTSDEVISES") != null)
				? Double.parseDouble(data.get("DEPOTSDEVISES"))
				: null;
		String currencyDepbDevise = (data.get("DEVISES_DEPOTSDEVISES") != ""
				&& data.get("DEVISES_DEPOTSDEVISES") != null) ? data.get("DEVISES_DEPOTSDEVISES") : null;
		Double depconf = (data.get("DEPOTSCONF") != "" && data.get("DEPOTSCONF") != null)
				? Double.parseDouble(data.get("DEPOTSCONF"))
				: null;
		String shareColValue = (data.get("ACTIONS") != "" && data.get("ACTIONS") != null) ? data.get("ACTIONS") : null;
		Double veicleColValue = (data.get("VEICLES") != "" && data.get("VEICLES") != null)
				? Double.parseDouble(data.get("VEICLES"))
				: null;
		String currencyVehicleColValue = (data.get("CURRENCY_VEICLE_COL_VALUE") != ""
				&& data.get("CURRENCY_VEICLE_COL_VALUE") != null) ? data.get("CURRENCY_VEICLE_COL_VALUE") : null;
		Double valeurVehicule = (data.get("VALEUR_VEHICULE") != "" && data.get("VALEUR_VEHICULE") != null)
				? Double.parseDouble(data.get("VALEUR_VEHICULE"))
				: null;
		String currencyValeurVehicule = (data.get("CURRENCY_VALEUR_VEHICULE") != ""
				&& data.get("CURRENCY_VALEUR_VEHICULE") != null) ? data.get("CURRENCY_VALEUR_VEHICULE") : null;
		Double bankingLgColValue = (data.get("GARANTIES BANQUES") != "" && data.get("GARANTIES BANQUES") != null)
				? Double.parseDouble(data.get("GARANTIES BANQUES"))
				: null;
		String currencyBankingLgColValue = (data.get("DEVISE_GARANTIES BANQUES") != ""
				&& data.get("DEVISE_GARANTIES BANQUES") != null) ? data.get("DEVISE_GARANTIES BANQUES") : null;

		Client client = Client.clientFromJSON(data);

		// converting string to date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yy-MM-dd");
		DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("dd-MM-yy");
		LocalDate maturityDateToInsert = (maturityDate != null && maturityDate != "")
				? LocalDate.parse(maturityDate, maturityDate.length() == 10 ? formatter2 : formatter4)
				: null;
		LocalDate firstInstalToInsert = (firstInstal != null && firstInstal != "")
				? LocalDate.parse(firstInstal, firstInstal.length() == 10 ? formatter2 : formatter3)
				: null;

		LocalDate defaultDateToInsert = (defaultDate != null && defaultDate != "")
				? LocalDate.parse(defaultDate, defaultDate.length() == 10 ? formatter2 : formatter3)
				: null;

		LocalDate restructuredDateToInsert = (restructuredDate != null && restructuredDate != "")
				? LocalDate.parse(restructuredDate, restructuredDate.length() == 10 ? formatter2 : formatter3)
				: null;

		Engagement engagement = new Engagement(0L, facilityId, facilityType, accountNumber, mainProductDesc,
				productDescription, productCode, nominalExposure, effectiveProvAmt, intResv, pca, part_20700_20710,
				islamic, leasing, maturityDateToInsert, maturityDays, lcNumber, ifrsBalance, installmentPayementCycle,
				isoCurCode, ageOfLoan, firstInstalToInsert, loanCurrency, nominalInterestRate, limite, glSubhead,
				glDescription, impayes, defaultDateToInsert, dpd, restructuredDateToInsert, reasonForNpa,
				reasonForNpaDesc, realEstateColValue, currencyRealEstateColValue, valeurexpertise,
				currencyValeur_expertise, cashColValue, currencyCashColValue, depbdzd, depbdevise, currencyDepbDevise,
				depconf, shareColValue, veicleColValue, currencyVehicleColValue, valeurVehicule, currencyValeurVehicule,
				bankingLgColValue, currencyBankingLgColValue, client);

		return engagement;

	}

}
