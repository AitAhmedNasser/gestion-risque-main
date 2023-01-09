package it.gestionRisque.app.portefeuil;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PortefeuilDirectDTO {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class PortfeuilleDirectBetweenTwoDate {
		private String date_debut;
		private String date_fin;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class PortfeuilleDirectOnSpecificDate {
		private String date_reporting;

	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonFormat(shape= JsonFormat.Shape.ARRAY)
	
	public static class PortfeuilleDirectresponse {
		String reporting_date;
		Double solde_balance;

	}
	
	
}
