package it.gestionRisque.app.Repporting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortefeuilleIndirect {
 private Long id;
 private String types;
 private Double ouvertureLettreCredit;
 private Double cautions;
 private Double aval;
 private Double obligationCautionneeDuanee;

}
