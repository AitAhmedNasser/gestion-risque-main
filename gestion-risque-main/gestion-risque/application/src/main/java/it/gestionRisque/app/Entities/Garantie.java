package it.gestionRisque.app.Entities;

import java.util.Map;

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


	public static Garantie garantieFromJSON(Map<String, String> data) {
		
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
		 Double shareColValue = (data.get("SHARE_COL_VALUE")!= "" && data.get("SHARE_COL_VALUE")!=null)? Double.parseDouble(data.get("SHARE_COL_VALUE")):null;
		 Double veicleColValue = (data.get("VEICLE_COL_VALUE")!= "" && data.get("VEICLE_COL_VALUE")!=null)? Double.parseDouble(data.get("VEICLE_COL_VALUE")):null;
		 String currencyVehicleColValue = (data.get("CURRENCY_VEICLE_COL_VALUE")!= "" && data.get("CURRENCY_VEICLE_COL_VALUE")!=null)? data.get("CURRENCY_VEICLE_COL_VALUE"):null;
		 Double  valeurVehicule = (data.get("VALEUR_VEHICULE")!= "" && data.get("VALEUR_VEHICULE")!=null)? Double.parseDouble(data.get("VALEUR_VEHICULE")):null;
		 String currencyValeurVehicule = (data.get("CURRENCY_VALEUR_VEHICULE")!= "" && data.get("CURRENCY_VALEUR_VEHICULE")!=null)? data.get("CURRENCY_VALEUR_VEHICULE"):null;
		 Double bankingLgColValue = (data.get("BANKING_LG_COL_VALUE")!= "" && data.get("BANKING_LG_COL_VALUE")!=null)? Double.parseDouble(data.get("BANKING_LG_COL_VALUE")):null;
		 String currencyBankingLgColValue = (data.get("CURRENCY_BANKING_LG_COL_VALUE")!= "" && data.get("CURRENCY_BANKING_LG_COL_VALUE")!=null)? data.get("CURRENCY_BANKING_LG_COL_VALUE"):null;
		 
		 Garantie garantie = new Garantie(0,realEstateColValue, currencyRealEstateColValue, valeurexpertise, currencyValeur_expertise,
				 cashColValue, currencyCashColValue, depbdzd, depbdevise, currencyDepbDevise, depconf, shareColValue, veicleColValue,
				 currencyVehicleColValue, valeurVehicule, currencyValeurVehicule, bankingLgColValue,  currencyBankingLgColValue );
		 
		 return garantie;
	}

}
