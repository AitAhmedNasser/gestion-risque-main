//package Auth.entities;
package it.gestionRisque.app.auth.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@Column(name = "region", length = 2)
	private String region;

	@OneToMany(mappedBy = "agence")
	@JsonIgnore
	private List<User> users = new ArrayList<>();

}
