package it.gestionRisque.app.di.Services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Repositories.ClientRepository;
import it.gestionRisque.app.Repositories.EngagementRepository;



@Service
public class ClientService {
	
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	EngagementRepository engagementRepository;
	
	@Transactional
	public void saveAllClients(List<Client> clients) {
		 clientRepository.saveAll(clients);
		 
	}
	
	
	
	public List<String[]> findBySecteur(String daterepo) {
		return clientRepository.findGroupedBySecteur(daterepo);
	}
	
	public List<String[]> findTotalBySecteur(String daterepo) {
		return clientRepository.findTotalGroupedBySecteur(daterepo);
	}
	
	public List<String[]> findByZone(String daterepo) {
		return clientRepository.findGroupedByZone(daterepo);
	}
	
	public List<String[]> findTotalByZone(String daterepo) {
		return clientRepository.findTotalGroupedByZone(daterepo);
	}
	
	
	public List<String[]> findTauxByZone(String daterepo) {
		
		List<String[]> list = clientRepository.findGroupedByZone(daterepo);
		String[] total = clientRepository.findTotalGroupedByZone(daterepo).get(0);
		List<String[]> listTaux = new ArrayList<>();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.applyPattern("#0.##");
		Double tx ;
		
		for (String[] st:list) {
			tx = (Double.parseDouble(st[0]) / Double.parseDouble(total[0]))*100;
			String[] taux = {"",""};
			//System.out.println("list : "+ Double.parseDouble(st[0]) / Double.parseDouble(total[0]) ) ;
			taux[0] = df.format(tx) + " %";
			taux[1] = st[1];
			listTaux.add(taux);
		}
		return listTaux;
	}
	
	
public List<String[]> findTauxBySecteur(String daterepo) {
		
		List<String[]> list = clientRepository.findGroupedBySecteur(daterepo);
		String[] total = clientRepository.findTotalGroupedBySecteur(daterepo).get(0);
		List<String[]> listTaux = new ArrayList<>();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.applyPattern("#0.##");
		Double tx1 ;
		Double tx2 ;
		
		for (String[] st:list) {
			tx1 = (Double.parseDouble(st[1]) / Double.parseDouble(total[0]))*100;
			tx2 = (Double.parseDouble(st[2]) / Double.parseDouble(total[1]))*100;
			String[] taux = {"","",""};
			//System.out.println("list : "+ Double.parseDouble(st[0]) / Double.parseDouble(total[0]) ) ;
			taux[0] = st[0];
			taux[1] =  df.format(tx1) + " %";
			taux[2] =  df.format(tx2) + " %";
			listTaux.add(taux);
		}
		return listTaux;
	}


public List<String[]> findTotalTauxBySecteur(String daterepo) {
	
	List<String[]> list = clientRepository.findGroupedBySecteur(daterepo);
	String[] total = clientRepository.findTotalGroupedBySecteur(daterepo).get(0);
	List<String[]> listTaux = new ArrayList<>();
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	df.applyPattern("#0.##");
	Double tx1 ;
	Double tx2 ;
	
	Double ttx1 = 0D;
	Double ttx2 = 0D;
	
	for (String[] st:list) {
		tx1 = (Double.parseDouble(st[1]) / Double.parseDouble(total[0]))*100;
		tx2 = (Double.parseDouble(st[2]) / Double.parseDouble(total[1]))*100;
		
		ttx1 += tx1;
		ttx2 += tx2;
		//System.out.println("list : "+ Double.parseDouble(st[0]) / Double.parseDouble(total[0]) ) ;
		
	}
	String[] taux = {"",""};
	taux[0] =  df.format(ttx1) + " %";
	taux[1] =  df.format(ttx2) + " %";
	listTaux.add(taux);
	return listTaux;
}

	
	
	
	
//	public  Client clientFromJSON(Map<String, String> data) {
//		 String reportingDate = (data.get("REPORTING_DATE")!= "" && data.get("REPORTING_DATE")!=null)? data.get("REPORTING_DATE"):null;	
//		 String obligorName = (data.get("OBLIGOR_NAME")!= "" && data.get("OBLIGOR_NAME")!=null)? data.get("OBLIGOR_NAME"):null;
//		 String obligorId = (data.get("OBLIGOR_ID")!= "" && data.get("OBLIGOR_ID")!=null)? data.get("OBLIGOR_ID"):null;
//		 String customerType = (data.get("CUSTOMER_TYPE")!= "" && data.get("CUSTOMER_TYPE")!=null)? data.get("CUSTOMER_TYPE"):null;
//		 Integer entitieCode =  (data.get("ENTITY_CODE")!= "" && data.get("ENTITY_CODE")!=null)? Integer.parseInt(data.get("ENTITY_CODE")):null;
//		 String borrowerType = (data.get("BORROWER_TYPE")!= "" && data.get("BORROWER_TYPE")!=null)? data.get("BORROWER_TYPE"):null;
//		 String groupId = (data.get("GROUP_ID")!= "" && data.get("GROUP_ID")!=null)? data.get("GROUP_ID"):null;
//		 String groupName = (data.get("GROUP_NAME")!= "" && data.get("GROUP_NAME")!=null)? data.get("GROUP_NAME"):null;
//		 String solId = (data.get("SOL_NAME")!= "" && data.get("SOL_NAME")!=null)? data.get("SOL_NAME"):null;
//		 String solDescription = (data.get("SOL_DESCRIPTION")!= "" && data.get("SOL_DESCRIPTION")!=null)? data.get("SOL_DESCRIPTION"):null;
//		 String defaultFlagMain = (data.get("DEFAULT_FLAG_MAIN")!= "" && data.get("DEFAULT_FLAG_MAIN")!=null)? data.get("DEFAULT_FLAG_MAIN"):null;
//		 String defaultFlagSub = (data.get("DEFAULT_FLAG_SUB")!= "" && data.get("DEFAULT_FLAG_SUB")!=null)? data.get("DEFAULT_FLAG_SUB"):null;
//		 String wliyaCode = (data.get("WILAYA_CODE")!= "" && data.get("WILAYA_CODE")!=null)? data.get("WILAYA_CODE"):null;
//		 String wilayaCodeDescription =  (data.get("WILAYA_CODE_DESCRIPTION")!= "" && data.get("WILAYA_CODE_DESCRIPTION")!=null)? data.get("WILAYA_CODE_DESCRIPTION"):null;
//		 Double soldeBalance =(data.get("SOLDE_BALANCE")!= "" && data.get("SOLDE_BALANCE")!=null)? Double.parseDouble(data.get("SOLDE_BALANCE")):null;
//		 String industryCode = (data.get("INDUSTRY_CODE")!= "" && data.get("INDUSTRY_CODE")!=null)? data.get("INDUSTRY_CODE"):null;
//		 String industryCodeDesc = (data.get("INDUSTRY_CODE_DESC")!= "" && data.get("INDUSTRY_CODE_DESC")!=null)? data.get("INDUSTRY_CODE_DESC"):null;
//		 String subsectorCode = (data.get("SUBSECTOR_CODE")!= "" && data.get("SUBSECTOR_CODE")!=null)? data.get("SUBSECTOR_CODE"):null;
//		 String subsectorCodeDesc = (data.get("SUBSECTOR_CODE_DESC")!= "" && data.get("SUBSECTOR_CODE_DESC")!=null)? data.get("SUBSECTOR_CODE_DESC"):null;
//		 
//		 
//			Client client = new Client(reportingDate,obligorName,obligorId,customerType,entitieCode,borrowerType,
//					groupId, groupName, solId, solDescription, defaultFlagMain, defaultFlagSub, wliyaCode,
//					wilayaCodeDescription, soldeBalance, industryCode, industryCodeDesc, subsectorCode, subsectorCodeDesc );
//			
//			
//			return client;
//			
//		}

}
