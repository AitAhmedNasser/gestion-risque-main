//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.gestionRisque.app.Entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Agence implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Nom_agence")
	private String agenceName;

	@Column(name = "Description")
	private String description;

	@Column(name = "Code_agence")
	private Long codeAgence;

	@Column(name = "Zone_agence")
	private String zoneAgence;

	@OneToMany(mappedBy = "agence")
	@JsonIgnore
	private List<User> users = new ArrayList<>();

	public static Agence agenceFromJSON(Map<String, String> data) {
		String zone = "";
		Long codeAgence = (data.get("CODE_AGENCE") != "" && data.get("CODE_AGENCE") != null)
				? Long.parseLong(data.get("CODE_AGENCE"))
				: null;

		String agenceName = (data.get("DESC_AGENCE") != "" && data.get("DESC_AGENCE") != null) ? data.get("DESC_AGENCE")
				: null;

		String description = (data.get("DESC_AGENCE") != "" && data.get("DESC_AGENCE") != null)
				? data.get("DESC_AGENCE")
				: null;

		List<User> users = null;

		switch (agenceName.toLowerCase().trim()) {
		case "dar elbeida":
			zone = "C";
			break;
		case "blida":
			zone = "C";
			break;
		case "oran":
			zone = "O";
			break;
		case "setif":
			zone = "E";
			break;
		case "hassi massaoued":
			zone = "S";
			break;
		case "betna":
			zone = "C";
			break;
		case "telemcen":
			zone = "O";
			break;
		default:
			zone = null;
		}

		Agence agence = new Agence((long) 0, agenceName, description, codeAgence, zone, users);

		return agence;
	}

}
