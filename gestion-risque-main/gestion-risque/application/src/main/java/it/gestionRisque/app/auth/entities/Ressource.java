//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Ressource")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ressource implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="nom_Res")
	@Nullable
	private String name;
	
	@Column(name="Description")
	private String Description;

	@OneToMany(mappedBy = "ressources")
	@JsonIgnore
	private List<Permissions> permissions = new ArrayList<>();

}
