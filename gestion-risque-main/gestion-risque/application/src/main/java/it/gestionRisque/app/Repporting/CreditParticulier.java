package it.gestionRisque.app.Repporting;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditParticulier {
	       @Id
	private long id; 
	private Double creditTotaldirect;
	private Double creanceDouteuse;
	private Double creanceCourant;
	private Double interetReserve;
	private  Double provisions;
	private Double tauxCreanceDouteuse;
	private Double tauxOuverture;
	private  Double InteretreservesCreancesDouteuse;
	private Double creanceDouteuseNets ;
	private Double creditDirectNetInteretReserve;
}
