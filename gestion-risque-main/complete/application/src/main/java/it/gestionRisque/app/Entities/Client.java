package it.gestionRisque.app.Entities;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.poi.ss.usermodel.DateUtil;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Table(name= "ClientsTable")
@Entity
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
	public static Client clientFromJSON(Map<String, String> data) {
	 String obligorId = (data.get("OBLIGOR_ID")!= "" && data.get("OBLIGOR_ID")!=null)? data.get("OBLIGOR_ID"):null;
	 Double reportingDate = (data.get("REPORTING_DATE")!= "" && data.get("REPORTING_DATE")!=null)? Double.parseDouble(data.get("REPORTING_DATE")):null;	
	 String obligorName = (data.get("OBLIGOR_NAME")!= "" && data.get("OBLIGOR_NAME")!=null)? data.get("OBLIGOR_NAME"):null;
	 String customerType = (data.get("CUSTOMER_TYPE")!= "" && data.get("CUSTOMER_TYPE")!=null)? data.get("CUSTOMER_TYPE"):null;
	 Integer entitieCode =  (data.get("ENTITY_CODE")!= "" && data.get("ENTITY_CODE")!=null)? Integer.parseInt(data.get("ENTITY_CODE")):null;
	 String borrowerType = (data.get("BORROWER_TYPE")!= "" && data.get("BORROWER_TYPE")!=null)? data.get("BORROWER_TYPE"):null;
	 String groupId = (data.get("GROUP_ID")!= "" && data.get("GROUP_ID")!=null)? data.get("GROUP_ID"):null;
	 String groupName = (data.get("GROUP_NAME")!= "" && data.get("GROUP_NAME")!=null)? data.get("GROUP_NAME"):null;
	 String solId = (data.get("SOL_NAME")!= "" && data.get("SOL_NAME")!=null)? data.get("SOL_NAME"):null;
	 String solDescription = (data.get("SOL_DESCRIPTION")!= "" && data.get("SOL_DESCRIPTION")!=null)? data.get("SOL_DESCRIPTION"):null;
	 String defaultFlagMain = (data.get("DEFAULT_FLAG_MAIN")!= "" && data.get("DEFAULT_FLAG_MAIN")!=null)? data.get("DEFAULT_FLAG_MAIN"):null;
	 String defaultFlagSub = (data.get("DEFAULT_FLAG_SUB")!= "" && data.get("DEFAULT_FLAG_SUB")!=null)? data.get("DEFAULT_FLAG_SUB"):null;
	 String defaultFlageDesc = (data.get("DEFAULT_FLAG_DESC")!= "" && data.get("DEFAULT_FLAG_DESC")!=null)? data.get("DEFAULT_FLAG_DESC"):null;
	 String wliyaCode = (data.get("WILAYA_CODE")!= "" && data.get("WILAYA_CODE")!=null)? data.get("WILAYA_CODE"):null;
	 String wilayaCodeDescription =  (data.get("WILAYA_CODE_DESCRIPTION")!= "" && data.get("WILAYA_CODE_DESCRIPTION")!=null)? data.get("WILAYA_CODE_DESCRIPTION"):null;
	 String industryCode = (data.get("INDUSTRY_CODE")!= "" && data.get("INDUSTRY_CODE")!=null)? data.get("INDUSTRY_CODE"):null;
	 String industryCodeDesc = (data.get("INDUSTRY_CODE_DESC")!= "" && data.get("INDUSTRY_CODE_DESC")!=null)? data.get("INDUSTRY_CODE_DESC"):null;
	 String subsectorCode = (data.get("SUBSECTOR_CODE")!= "" && data.get("SUBSECTOR_CODE")!=null)? data.get("SUBSECTOR_CODE"):null;
	 String subsectorCodeDesc = (data.get("SUBSECTOR_CODE_DESC")!= "" && data.get("SUBSECTOR_CODE_DESC")!=null)? data.get("SUBSECTOR_CODE_DESC"):null;
	 
	 String reportingDateToInsert;
	 Date javaDate = DateUtil.getJavaDate((double) reportingDate);
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
