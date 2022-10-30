package Auth.entities;

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
	
	@OneToMany(mappedBy = "roles")
	private List<Niveau> niveaux = new ArrayList<>();

		@OneToMany(mappedBy = "roles")
		@JsonIgnore
		private List<User> users = new ArrayList<>();
		
		@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		 private Set<Permissions> permissions = new HashSet();
		
}
