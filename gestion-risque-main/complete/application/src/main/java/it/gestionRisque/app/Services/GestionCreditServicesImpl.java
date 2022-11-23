package it.gestionRisque.app.Services;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repositories.ClientRepository;
import it.gestionRisque.app.Repositories.CompteRepository;
import it.gestionRisque.app.Repositories.EngagementRepository;
import it.gestionRisque.app.Repporting.AnalyseProtefeuilleIndirect;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import it.gestionRisque.app.Utils.CreditUtils;
import it.gestionRisque.app.exception.GestionCreditException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor 
@Data
@Service

@Transactional
public class GestionCreditServicesImpl implements GestionCreditService {

 private ClientRepository clientRepository;
 private EngagementRepository engagementRepository;
private CompteRepository compteRepository;
private 	List<Client> clientlis= new ArrayList();



// ajout au client  avec la necessite de verfication  l existance de de obligoreid et reporting date 

	@Override
	public Client addToClient(Client client) {
		/*
		 * Client c = new Client();
		 * 
		 * 
		 * 
		 * Optional<Client> clientByobligoreid =
		 * clientRepository.findByObligoreIdAndReportingDate(client.getObligoreId(),
		 * client.getReportingDate()); if(clientByobligoreid.isPresent()) {
		 * System.out.println("obligor name existee deja"); }else { c =
		 * clientRepository.save(client); }
		 * 
		 */
	return null;
	}

	
	// affichage de tous les clients
	@Override
	public List<Client> getallClient() {
		List<Client> allClient= new ArrayList<Client>();
		allClient = clientRepository.findAll();
		return allClient;
	}

	@Override
	public ResponseEntity<Client> updateProduit(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//engagament 
	
	 // ajout a engagemnt
	 //et faire update sur la table client  si il ont dans la mm date de reporting  dans la methode ****allEngagementByClient();*****
	
	@Override
	public Engagement addToengagement(Engagement engagement) {
		
		Engagement en = engagementRepository.save(engagement);
		return  en;
	}


	
	
	
	
	
	//compte

	@Override
	public Compte addTocompte(Compte compte) {

	
		return compteRepository.save(compte);
	}

	@Override
	public List<Compte> getAllcompte() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	// clacule pour credit particulier pour repporting 
	@Override
	public CreditParticulier addToCredit(String dateREPO) throws ParseException {
			
		HashMap< String ,Double> map = new HashMap<String, Double>();
		HashMap< Long ,Double> IntervRes = new HashMap<Long, Double>();
		HashMap< Long ,Double> efect = new HashMap<Long, Double>();
		HashMap< Long ,Double> nominaleExpo = new HashMap<Long, Double>();
		HashMap< Long ,Double> interreservGL = new HashMap<Long, Double>();
		CreditParticulier creditPar = new CreditParticulier();
		List<Engagement>engList= engagementRepository.findByDateReporting(dateREPO);
		Double x_IntResv =0.0;
		Double x_EffectiveProvAmt=0.0 ;
		Double x_NominalExposure=0.0;
		
		
		
		for(Engagement eng :engList ){
			Client c = eng.getClient();
			if(eng.getIntResv() == null || eng.getIntResv() == 0) {
				x_IntResv =0D;
			}else {
				x_IntResv=eng.getIntResv();
			}		
			if(eng.getEffectiveProvAmt()== null|| eng.getEffectiveProvAmt() ==0) {
				x_EffectiveProvAmt =0D;
	
			}else {
				x_EffectiveProvAmt=eng.getEffectiveProvAmt();
				
			}
		
						if((c.getBorrowerType().equals(CreditUtils.CUSTOMER_TYPERetail))&&(eng.getFacilityType().equals(CreditUtils.FACILITY_TYPE))) {
						
							map.put(c.getObligoreId(), c.getSoldeBalance());
							
							IntervRes.put(eng.getId(),x_IntResv);
							
							efect.put(eng.getId(), x_EffectiveProvAmt);
					
							 double soldBaSom = map.values().stream().mapToDouble(d->d).sum();
							
							 double SomIntervRes= IntervRes.values().stream().mapToDouble(Double::intValue).sum();
							 double somEffect = efect.values().stream().mapToDouble(Double::intValue).sum();
					
							creditPar.setCreditTotaldirect(soldBaSom);
							creditPar.setInteretReserve(SomIntervRes);
							creditPar.setProvisions(somEffect);
							
							if(eng.getGlSubhead().startsWith(CreditUtils.GL_SUBHEAD)) {
								if(eng.getNominalExposure() == null) {
									x_NominalExposure = 0D;
								}else {
									x_NominalExposure=eng.getNominalExposure();
								}
								nominaleExpo.put(eng.getId(), x_NominalExposure);
								double nominalexpo = nominaleExpo.values().stream().mapToDouble(d->d).sum();
							
								creditPar.setCreanceDouteuse(nominalexpo);

								interreservGL.put(eng.getId(), x_IntResv);
								double totalintervre = interreservGL.values().stream().mapToDouble(d->d).sum();
								creditPar.setInteretreservesCreancesDouteuse(totalintervre);
									
							}
							if(!(creditPar.getCreanceDouteuse()== null)) {
								Double creanceCour = creditPar.getCreditTotaldirect()-creditPar.getCreanceDouteuse();
								creditPar.setCreanceCourant(creanceCour);
							
								Double diffDouteurProvisions= creditPar.getCreanceDouteuse()-creditPar.getProvisions();
								if(diffDouteurProvisions==0) {
									creditPar.setTauxOuverture(null);
								}else {
								 	Double tauxcouver=creditPar.getInteretReserve() /diffDouteurProvisions;
								 	creditPar.setTauxOuverture(tauxcouver);
								}
								
								double creancedouteuseNet = creditPar.getCreanceDouteuse()-creditPar.getProvisions();
							 	creditPar.setCreanceDouteuseNets(creancedouteuseNet);
								
							}
							if (!(creditPar.getCreanceCourant()==null)) {
								 Double diffCreditTotalCreanceCour =creditPar.getCreditTotaldirect()-creditPar.getCreanceCourant();
								if(diffCreditTotalCreanceCour==0) {
									creditPar.setTauxCreanceDouteuse(null);
									 }else {
									 double tauxDefaux = (creditPar.getCreanceDouteuse()-creditPar.getProvisions())/diffCreditTotalCreanceCour;
									 creditPar.setTauxCreanceDouteuse(tauxDefaux); 
									}
								double creditdirectNet = creditPar.getCreditTotaldirect()-creditPar.getCreanceCourant();
							 	creditPar.setCreditDirectNetInteretReserve(creditdirectNet);
				        		
							}
	
										
						}
					  
				  }
		
		return creditPar;
	}
	
	// clacule pour credit entreprise pour repporting 

	@Override
	public CreditEntreprise addToCreditEntre(String dateREPO) throws ParseException  {
		HashMap< String ,Double> map = new HashMap<String, Double>();
		HashMap< Long ,Double> IntervRes = new HashMap<Long, Double>();
		HashMap< Long ,Double> efect = new HashMap<Long, Double>();
		HashMap< Long ,Double> nominaleExpo = new HashMap<Long, Double>();
		HashMap< Long ,Double> interreservGL = new HashMap<Long, Double>();
		CreditEntreprise creditEnt = new CreditEntreprise();
		Double x_IntResv =0.0;
		Double x_EffectiveProvAmt=0.0 ;
		Double x_NominalExposure=0.0;
		
	
		List<Engagement>engList= engagementRepository.findByDateReporting(dateREPO);
		 List<CreditEntreprise> listCreditEntreprise = new ArrayList<CreditEntreprise>();
		
			for(Engagement eng :engList ){
				Client c = eng.getClient();
				if(eng.getIntResv() == null || eng.getIntResv() == 0) {
					x_IntResv =0D;
				}else {
					x_IntResv=eng.getIntResv();
				}		
				if(eng.getEffectiveProvAmt()== null|| eng.getEffectiveProvAmt() ==0) {
					x_EffectiveProvAmt =0D;
		
				}else {
					x_EffectiveProvAmt=eng.getEffectiveProvAmt();
					
				}
			
							if((c.getBorrowerType().equals(CreditUtils.CUSTOMER_TYPECORP))&&(eng.getFacilityType().equals(CreditUtils.FACILITY_TYPE))) {
								map.put(c.getObligoreId(), c.getSoldeBalance());
								
								IntervRes.put(eng.getId(),x_IntResv);
								
								efect.put(eng.getId(), x_EffectiveProvAmt);
						
								 double soldBaSom = map.values().stream().mapToDouble(d->d).sum();
								
								 double SomIntervRes= IntervRes.values().stream().mapToDouble(Double::intValue).sum();
								 double somEffect = efect.values().stream().mapToDouble(Double::intValue).sum();
						
								creditEnt.setCreditTotaldirect(soldBaSom);
								creditEnt.setInteretReserve(SomIntervRes);
								creditEnt.setProvisions(somEffect);
								
								if(eng.getGlSubhead().startsWith(CreditUtils.GL_SUBHEAD)) {
									if(eng.getNominalExposure() == null) {
										x_NominalExposure = 0D;
									}else {
										x_NominalExposure=eng.getNominalExposure();
									}
									nominaleExpo.put(eng.getId(), x_NominalExposure);
									double nominalexpo = nominaleExpo.values().stream().mapToDouble(d->d).sum();
								
									creditEnt.setCreanceDouteuse(nominalexpo);

									interreservGL.put(eng.getId(), x_IntResv);
									double totalintervre = interreservGL.values().stream().mapToDouble(d->d).sum();
									creditEnt.setInteretreservesCreancesDouteuse(totalintervre);
										
								}
								if(!(creditEnt.getCreanceDouteuse()== null)) {
									Double creanceCour = creditEnt.getCreditTotaldirect()-creditEnt.getCreanceDouteuse();
									creditEnt.setCreanceCourant(creanceCour);
								
									Double diffDouteurProvisions= creditEnt.getCreanceDouteuse()-creditEnt.getProvisions();
									if(diffDouteurProvisions==0) {
										creditEnt.setTauxOuverture(null);
									}else {
									 	Double tauxcouver=creditEnt.getInteretReserve() /diffDouteurProvisions;
									 	creditEnt.setTauxOuverture(tauxcouver);
									}
									
									double creancedouteuseNet = creditEnt.getCreanceDouteuse()-creditEnt.getProvisions();
									creditEnt.setCreanceDouteuseNets(creancedouteuseNet);
									
								}
								if (!(creditEnt.getCreanceCourant()==null)) {
									 Double diffCreditTotalCreanceCour =creditEnt.getCreditTotaldirect()-creditEnt.getCreanceCourant();
									if(diffCreditTotalCreanceCour==0) {
										creditEnt.setTauxCreanceDouteuse(null);
										 }else {
										 double tauxDefaux = (creditEnt.getCreanceDouteuse()-creditEnt.getProvisions())/diffCreditTotalCreanceCour;
										 creditEnt.setTauxCreanceDouteuse(tauxDefaux); 
										}
									double creditdirectNet = creditEnt.getCreditTotaldirect()-creditEnt.getCreanceCourant();
									creditEnt.setCreditDirectNetInteretReserve(creditdirectNet);
					        		
								}
		
											
							}
						  
					  }
			
		return creditEnt;
	}

	
	//  recherche des client par date reporting 
	@Override
	public List<String> getClientByDateRepo(Integer year) {
		List<String> daterepo= new  ArrayList<String>();
		
		daterepo = clientRepository.findByReportingDate(year);
		return daterepo;
	}


	@Override
	public AnalyseProtefeuilleIndirect addtPortefeuilleIndirect(String dateRepor) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	
	

	
	
	
	}

	
	



	


