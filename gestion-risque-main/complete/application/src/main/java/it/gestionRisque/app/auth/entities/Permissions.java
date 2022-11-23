//package Auth.entities;
package it.gestionRisque.app.auth.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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

@Entity
@Table
public class Permissions implements Serializable {


	public Permissions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Permissions(Privilege privileges, Ressource ressources, String namepermission) {
		super();
		this.privileges = privileges;
		this.ressources = ressources;
		this.namepermission = namepermission;
	}

	public String getNamepermission() {
		return namepermission;
	}

	public void setNamepermission(String namepermission) {
		this.namepermission = namepermission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Privilege getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Privilege privileges) {
		this.privileges = privileges;
	}

	public Ressource getRessources() {
		return ressources;
	}

	public void setRessources(Ressource ressources) {
		this.ressources = ressources;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	

	 @ManyToOne
	 private Privilege privileges;
	 

	 @ManyToOne
	 private Ressource ressources; 

	 @Column(name = "Permission_Name")
	 @JsonProperty( access = Access.READ_ONLY)
	 private String namepermission;


		
}
