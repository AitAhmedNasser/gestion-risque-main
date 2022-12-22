//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="niveau")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Niveau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idNiv;
	
	@Column(name = "nameniveau")
	private String nameNiveau;
	
	@Column(name = "Niveau")
	private Integer niveauNumber;
	

	
	@OneToMany(mappedBy = "niveaux",cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Role> roles = new ArrayList<>();



}
