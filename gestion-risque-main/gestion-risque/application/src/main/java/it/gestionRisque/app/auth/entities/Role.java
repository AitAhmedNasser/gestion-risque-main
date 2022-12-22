//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name="Roles")
@Data 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor@ToString
public class Role implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne()
	private Niveau niveaux;

		@OneToMany(mappedBy = "roles",cascade = CascadeType.ALL)
		@JsonIgnore
		private List<User> users = new ArrayList<>();
		
		@ManyToMany
		    @JoinTable(
		        name = "roles_permissions",
		        joinColumns = {
		            @JoinColumn(name = "role_id")
		        },
		        inverseJoinColumns = {
		            @JoinColumn(name = "permission_id")
		        }
		    )
		    Set < Permissions > permissions = new HashSet < Permissions > ();
}
