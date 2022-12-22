package it.gestionRisque.app.Repporting;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporting {
	Map<String, Object> entreprise;
	Map<String, Object> retail;
	Map<String, Object> totals;
}
