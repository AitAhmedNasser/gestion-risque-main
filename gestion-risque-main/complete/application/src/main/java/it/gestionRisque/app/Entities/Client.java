package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity(name= "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable{

@Id
@Column(name = "ID_CLIENT")
private String obligoreId;

@Column(name = "reportingDate")
private Date reportingDate;

@Column(name = "NOM_CLIENT")
private String obligoreName;

@Column(name = "TYPE_CLIENT")
private String customerType;

@Column(name = "entitieCode")
private Integer entitieCode;

@Column(name = "borrowerType")
private String  borrowerType;

@Column()
private String groupId;

@Column()
private String groupName;

@Column()
private String solId;

@Column()
private String solDescription;

@Column(name = "CLASS_PRINCIPALE_CLIENT")
private String defaultFlagMain;

@Column(name = "SOUS_CLASS_CLIENT")
private String defaultFlagSub;

@Column( name = "DESC_SOUS_CLASS_CLIENT")
private String defaultFlageDesc;

@Column(name = "CODE_WILAYA")
private String wilayaCode;

@Column(name = "DESC_WILAYA")
private String wilayaCodeDescription;

@Column(name = "CODE_SECTEUR")
private String industryCode;

@Column(name = "SOLDE_BALANCE")
private Double soldeBalance;

@Column(name = "DESC_SECTEUR")
private String industryCodeDesc;

@Column(name = "CODE_SOUS_SECTEUR")
private String subsectorCode;

@Column(name = "DESC_SOUS_SECTEUR")
private String subsectorCodeDesc;
 	 
@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
private List<Engagement> listengagement;
 
@OneToMany(mappedBy = "client", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
private List<Compte> listcompte;
 
// @OneToOne(cascade = {CascadeType.ALL})
//  private Risque risque;
 
public Client (String obligorId) {
	this.obligoreId  = obligorId;
}
}
