package it.gestionRisque.app.Entities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

import org.apache.poi.ss.usermodel.DateUtil;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity(name= "client")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ClientId.class)
public class Client implements Serializable{
 
@Id
@Column(name = "ID_CLIENT")
private String obligoreId;

@Id
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

@Column(name = "ID_GROUPE")
private String groupId;

@Column(name = "NOM_GROUPE")
private String groupName;

@Column(name = "ID_AGENCE")
private String solId;

@Column(name = "NOM_AGENCE")
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

// Constructor without others relations
public Client(String obligoreId, Date reportingDate,  String obligoreName,  String customerType,
	 Integer entitieCode,String  borrowerType,String groupId, String groupName, String solId,
	 String solDescription, String defaultFlagMain, String defaultFlagSub, String defaultFlageDesc,
	 String wilayaCode, String wilayaCodeDescription, String industryCode,
	 String industryCodeDesc, String subsectorCode, String subsectorCodeDesc) {
	 
	 this.obligoreId=obligoreId;
	 this.reportingDate=reportingDate;
	 this.obligoreName=obligoreName;
	 this.customerType=customerType;
	 this.entitieCode=entitieCode;
	 this.borrowerType=borrowerType;
	 this.groupId=groupId;
	 this.groupName=groupName;
	 this.solId=solId;
	 this.defaultFlagMain = defaultFlagMain;
	 this.defaultFlagSub=defaultFlagSub;
	 this.defaultFlageDesc = defaultFlageDesc;
	 this.wilayaCodeDescription=wilayaCodeDescription;
	 this.industryCode=industryCode;
	 this.industryCodeDesc=industryCodeDesc;
	 this.subsectorCode=subsectorCode;
	 this.subsectorCodeDesc=subsectorCodeDesc;
}

// ----  Client From JSON
	public static Client clientFromJSON(Map<String, String> data) throws ParseException {
	 String obligorId = (data.get("ID_CLIENT")!= "" && data.get("ID_CLIENT")!=null)? data.get("ID_CLIENT"):null;
	 String reportingDate = (data.get("Date")!= "" && data.get("Date")!=null)? (data.get("Date")):null;	
	 String obligorName = (data.get("NOM_CLIENT")!= "" && data.get("NOM_CLIENT")!=null)? data.get("NOM_CLIENT").trim():null;
	 String customerType = (data.get("TYPE_CLIENT")!= "" && data.get("TYPE_CLIENT")!=null)? data.get("TYPE_CLIENT"):null;
	 Integer entitieCode =  (data.get("ENTITY_CODE")!= "" && data.get("ENTITY_CODE")!=null)? Integer.parseInt(data.get("ENTITY_CODE").trim()):null;
	 String borrowerType = (data.get("TYPE_EMPRUNTEUR")!= "" && data.get("TYPE_EMPRUNTEUR")!=null)? data.get("TYPE_EMPRUNTEUR").trim():null;
	 String groupId = (data.get("ID_GROUPE")!= "" && data.get("ID_GROUPE")!=null)? data.get("ID_GROUPE"):null;
	 String groupName = (data.get("NOM_GROUPE")!= "" && data.get("NOM_GROUPE")!=null)? data.get("NOM_GROUPE"):null;
	 String solId = (data.get("CODE_AGENCE")!= "" && data.get("CODE_AGENCE")!=null)? data.get("CODE_AGENCE"):null;
	 String solDescription = (data.get("DESC_AGENCE")!= "" && data.get("DESC_AGENCE")!=null)? data.get("DESC_AGENCE"):null;
	 String defaultFlagMain = (data.get("CLASS_PRINCIPALE_CLIENT")!= "" && data.get("CLASS_PRINCIPALE_CLIENT")!=null)? data.get("CLASS_PRINCIPALE_CLIENT"):null;
	 String defaultFlagSub = (data.get("SOUS_CLASS_CLIENT")!= "" && data.get("SOUS_CLASS_CLIENT")!=null)? data.get("SOUS_CLASS_CLIENT"):null;
	 String defaultFlageDesc = (data.get("DESC_SOUS_CLASS_CLIENT")!= "" && data.get("DESC_SOUS_CLASS_CLIENT")!=null)? data.get("DESC_SOUS_CLASS_CLIENT"):null;
	 String wliyaCode = (data.get("CODE_ WILAYA")!= "" && data.get("CODE_ WILAYA")!=null)? data.get("CODE_ WILAYA"):null;
	 String wilayaCodeDescription =  (data.get("DESC_ WILAYA")!= "" && data.get("DESC_ WILAYA")!=null)? data.get("DESC_ WILAYA"):null;
	 String industryCode = (data.get("CODE_SECTEUR")!= "" && data.get("CODE_SECTEUR")!=null)? data.get("CODE_SECTEUR"):null;
	 String industryCodeDesc = (data.get("DESC_SECTEUR")!= "" && data.get("DESC_SECTEUR")!=null)? data.get("DESC_SECTEUR"):null;
	 String subsectorCode = (data.get("CODE_ SUBSECTOR")!= "" && data.get("CODE_ SUBSECTOR")!=null)? data.get("CODE_ SUBSECTOR"):null;
	 String subsectorCodeDesc = (data.get("DESC_SUBSECTOR")!= "" && data.get("DESC_SUBSECTOR")!=null)? data.get("DESC_SUBSECTOR"):null;
	 
	 String reportingDateToInsert;
	 Date javaDate =  new SimpleDateFormat("yyyy-MM-dd").parse(reportingDate);
	 if(reportingDate != null) {
		 
	//	 Date javaDate = DateUtil.getJavaDate((double) reportingDate);
	 //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
	 
	 
	  reportingDateToInsert = 
			 new SimpleDateFormat("yyyy-MM-dd").format(javaDate);
	 }else {
		  reportingDateToInsert = null;
	 }
	 
	 
		Client client = new Client(obligorId,javaDate,obligorName,customerType,entitieCode,borrowerType,
				groupId, groupName, solId, solDescription, defaultFlagMain, defaultFlagSub, defaultFlageDesc, 
				wliyaCode, wilayaCodeDescription, industryCode, industryCodeDesc,
				subsectorCode, subsectorCodeDesc);
		
		return client;
		
	}
}
