//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "Utilisateur")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User implements Serializable {

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = " Nom")
	private String Nom;

	@Column(name = " Prenom")
	private String Prenom;

	@Column(name = "Username")
	@Nullable()
	private String username;

	@Column(name = "email")
	@Nullable
	private String email;

	@Column(name = "password")
	@Nullable
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@ManyToOne
	private Role roles;

	@ManyToOne()
	private Agence agence;

}
