package it.gestionRisque.app.Services;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import antlr.StringUtils;
import it.gestionRisque.app.DynamicQueries.SearchCriteria;
import it.gestionRisque.app.DynamicQueries.SearchOperation;
import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Entities.RapportType;
import it.gestionRisque.app.Repositories.ClientRepository;
import it.gestionRisque.app.Repositories.CompteRepository;
import it.gestionRisque.app.Repositories.EngagementRepository;
import it.gestionRisque.app.Repositories.TotalEngagementRepository;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import it.gestionRisque.app.Repporting.PortefeuilleIndirect;
import it.gestionRisque.app.Utils.CreditUtils;
import it.gestionRisque.app.exception.GestionCreditException;
import it.gestionRisque.app.servicesRepoImpl.EngaggementRepositoryImpl;
//import it.gestionRisque.app.servicesRepoImpl.EngaggementRepositoryImpl;
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
	private TotalEngagementRepository rapportTypeRepository;
	private EngaggementRepositoryImpl engaggementrepositoryImpl;

	private List<Client> clientlis = new ArrayList();

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
		List<Client> allClient = new ArrayList<Client>();
		allClient = clientRepository.findAll();
		return allClient;
	}

	@Override
	public ResponseEntity<Client> updateProduit(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	// engagament

	// ajout a engagemnt
	// et faire update sur la table client si il ont dans la mm date de reporting
	// dans la methode ****allEngagementByClient();*****

	@Override
	public Engagement addToengagement(Engagement engagement) {

		Engagement en = engagementRepository.save(engagement);
		return en;
	}

	// compte

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
		 DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
		 
		 //decimal forma
		 Locale locale = new Locale("en", "UK");

		 DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		 symbols.setDecimalSeparator(',');
		 symbols.setGroupingSeparator(' ');

		 String pattern = "#,##0.##";
		 DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		 
		HashMap<String, Double> map = new HashMap<String, Double>();
		HashMap<Long, Double> IntervRes = new HashMap<Long, Double>();
		HashMap<Long, Double> efect = new HashMap<Long, Double>();
		HashMap<Long, Double> nominaleExpo = new HashMap<Long, Double>();
		HashMap<Long, Double> interreservGL = new HashMap<Long, Double>();
		CreditParticulier creditPar = new CreditParticulier();
		List<Engagement> engList = engagementRepository.findByDateReporting(dateREPO);
		Double x_IntResv = 0.0;
		Double x_EffectiveProvAmt = 0.0;
		Double x_NominalExposure = 0.0;

		for (Engagement eng : engList) {
			Client c = eng.getClient();
			if (eng.getIntResv() == null || eng.getIntResv() == 0) {
				x_IntResv = 0D;
			} else {
				x_IntResv = eng.getIntResv();
			}
			if (eng.getEffectiveProvAmt() == null || eng.getEffectiveProvAmt() == 0) {
				x_EffectiveProvAmt = 0D;

			} else {
				x_EffectiveProvAmt = eng.getEffectiveProvAmt();

			}

			if ((c.getBorrowerType().equals(CreditUtils.CUSTOMER_TYPERetail))
					&& (eng.getFacilityType().equals(CreditUtils.FACILITY_TYPE))) {

				map.put(c.getObligoreId(), c.getSoldeBalance());

				IntervRes.put(eng.getId(), x_IntResv);

				efect.put(eng.getId(), x_EffectiveProvAmt);

				double soldBaSom = map.values().stream().mapToDouble(d -> d).sum();

				double SomIntervRes = IntervRes.values().stream().mapToDouble(Double::intValue).sum();
				double somEffect = efect.values().stream().mapToDouble(Double::intValue).sum();
				// format double  with  personnal patern
			
				
			
				creditPar.setCreditTotaldirect(soldBaSom);
				creditPar.setInteretReserve(SomIntervRes);
				creditPar.setProvisions(somEffect);

				if (eng.getGlSubhead().startsWith(CreditUtils.GL_SUBHEAD)) {
					if (eng.getNominalExposure() == null) {
						x_NominalExposure = 0D;
					} else {
						x_NominalExposure = eng.getNominalExposure();
					}
					nominaleExpo.put(eng.getId(), x_NominalExposure);
					double nominalexpo = nominaleExpo.values().stream().mapToDouble(d -> d).sum();

					creditPar.setCreanceDouteuse(nominalexpo);

					interreservGL.put(eng.getId(), x_IntResv);
					double totalintervre = interreservGL.values().stream().mapToDouble(d -> d).sum();
					creditPar.setInteretreservesCreancesDouteuse(totalintervre);

				}
				if (!(creditPar.getCreanceDouteuse() == null)) {
					Double creanceCour = creditPar.getCreditTotaldirect() - creditPar.getCreanceDouteuse();
					creditPar.setCreanceCourant(creanceCour);

					Double diffDouteurProvisions = creditPar.getCreanceDouteuse() - creditPar.getInteretreservesCreancesDouteuse();
					if (diffDouteurProvisions == 0) {
						creditPar.setTauxOuverture(null);
					} else {
						Double tauxcouver = creditPar.getProvisions() / diffDouteurProvisions;
						creditPar.setTauxOuverture(tauxcouver);
					}

					double creancedouteuseNet = creditPar.getCreanceDouteuse() - creditPar.getInteretreservesCreancesDouteuse();
					creditPar.setCreanceDouteuseNets(creancedouteuseNet);

				}
				if (!(creditPar.getCreanceCourant() == null)) {
					Double diffCreditTotalCreanceCour = creditPar.getCreditTotaldirect()
							- creditPar.getInteretReserve();
					if (diffCreditTotalCreanceCour == 0) {
						creditPar.setTauxCreanceDouteuse(null);
					} else {
						double tauxDefaux = (creditPar.getCreanceDouteuse() - creditPar.getInteretreservesCreancesDouteuse())
								/ diffCreditTotalCreanceCour;
						creditPar.setTauxCreanceDouteuse(tauxDefaux);
					}
					double creditdirectNet = creditPar.getCreditTotaldirect() - creditPar.getInteretReserve();
					creditPar.setCreditDirectNetInteretReserve(creditdirectNet);

				}

			}

		}

		return creditPar;
	}

	// clacule pour credit entreprise pour repporting

	@Override
	public CreditEntreprise addToCreditEntre(String dateREPO) throws ParseException { 
		 DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
		 
		 //decimal forma
		 Locale locale = new Locale("en", "UK");

		 DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		 symbols.setDecimalSeparator(',');
		 symbols.setGroupingSeparator(' ');

		 String pattern = "#,##0.##";
		 
		HashMap<String, Double> map = new HashMap<String, Double>();
		HashMap<Long, Double> IntervRes = new HashMap<Long, Double>();
		HashMap<Long, Double> efect = new HashMap<Long, Double>();
		HashMap<Long, Double> nominaleExpo = new HashMap<Long, Double>();
		HashMap<Long, Double> interreservGL = new HashMap<Long, Double>();
		CreditEntreprise creditEnt = new CreditEntreprise();
		Double x_IntResv = 0.0;
		Double x_EffectiveProvAmt = 0.0;
		Double x_NominalExposure = 0.0;

		List<Engagement> engList = engagementRepository.findByDateReporting(dateREPO);
		List<CreditEntreprise> listCreditEntreprise = new ArrayList<CreditEntreprise>();

		for (Engagement eng : engList) {
			Client c = eng.getClient();
			if (eng.getIntResv() == null || eng.getIntResv() == 0) {
				x_IntResv = 0D;
			} else {
				x_IntResv = eng.getIntResv();
			}
			if (eng.getEffectiveProvAmt() == null || eng.getEffectiveProvAmt() == 0) {
				x_EffectiveProvAmt = 0D;

			} else {
				x_EffectiveProvAmt = eng.getEffectiveProvAmt();

			}

			if ((c.getBorrowerType().equals(CreditUtils.CUSTOMER_TYPECORP))
					&& (eng.getFacilityType().equals(CreditUtils.FACILITY_TYPE))) {
				map.put(c.getObligoreId(), c.getSoldeBalance());

				IntervRes.put(eng.getId(), x_IntResv);

				efect.put(eng.getId(), x_EffectiveProvAmt);

				double soldBaSom = map.values().stream().mapToDouble(d -> d).sum();

				double SomIntervRes = IntervRes.values().stream().mapToDouble(Double::intValue).sum();
				double somEffect = efect.values().stream().mapToDouble(Double::intValue).sum();

				creditEnt.setCreditTotaldirect(soldBaSom);
				creditEnt.setInteretReserve(SomIntervRes);
				creditEnt.setProvisions(somEffect);

				if (eng.getGlSubhead().startsWith(CreditUtils.GL_SUBHEAD)) {
					if (eng.getNominalExposure() == null) {
						x_NominalExposure = 0D;
					} else {
						x_NominalExposure = eng.getNominalExposure();
					}
					nominaleExpo.put(eng.getId(), x_NominalExposure);
					double nominalexpo = nominaleExpo.values().stream().mapToDouble(d -> d).sum();

					creditEnt.setCreanceDouteuse(nominalexpo);

					interreservGL.put(eng.getId(), x_IntResv);
					double totalintervre = interreservGL.values().stream().mapToDouble(d -> d).sum();
					creditEnt.setInteretreservesCreancesDouteuse(totalintervre);

				}
				if (!(creditEnt.getCreanceDouteuse() == null)) {
					Double creanceCour = creditEnt.getCreditTotaldirect() - creditEnt.getCreanceDouteuse();
					creditEnt.setCreanceCourant(creanceCour);

					Double diffDouteurProvisions = creditEnt.getCreanceDouteuse() - creditEnt.getInteretreservesCreancesDouteuse();
					if (diffDouteurProvisions == 0) {
						creditEnt.setTauxOuverture(null);
					} else {
						Double tauxcouver = creditEnt.getProvisions() / diffDouteurProvisions;
						creditEnt.setTauxOuverture(tauxcouver);
					}

					double creancedouteuseNet = creditEnt.getCreanceDouteuse() - creditEnt.getInteretReserve();
					creditEnt.setCreanceDouteuseNets(creancedouteuseNet);

				}
				if (!(creditEnt.getCreanceCourant() == null)) {
					Double diffCreditTotalCreanceCour = creditEnt.getCreditTotaldirect()- creditEnt.getInteretReserve();
					if (diffCreditTotalCreanceCour == 0) {
						creditEnt.setTauxCreanceDouteuse(null);
					} else {
						double tauxDefaux = (creditEnt.getCreanceDouteuse() - creditEnt.getInteretreservesCreancesDouteuse())
								/ diffCreditTotalCreanceCour;
						creditEnt.setTauxCreanceDouteuse(tauxDefaux);
					}
					double creditdirectNet = creditEnt.getCreditTotaldirect() - creditEnt.getInteretReserve();
					creditEnt.setCreditDirectNetInteretReserve(creditdirectNet);

				}

			}

		}

		return creditEnt;
	}

	// recherche des client par date reporting
	@Override
	public List<String> getClientByDateRepo(Integer year) {
		List<String> daterepo = new ArrayList<String>();

		daterepo = clientRepository.findByReportingDate(year);
		return daterepo;
	}

	/*
	 * @Override public List<Double> addtPortefeuilleIndirect() { List<Double> list
	 * = new ArrayList<Double>(); List<TotalEngagement> l =
	 * totalEngagementRepository.findAll(); l.forEach(a->{
	 * System.out.println(("select clause  " +a.getSelectClause()));
	 * System.out.println(" requ   "
	 * +engagementRepository.getTotalEngagement(a.getSelectClause(),
	 * a.getWhereClause()));
	 * list.add(engagementRepository.getTotalEngagement(a.getSelectClause(),
	 * a.getWhereClause())); }); return list; }
	 */

	@Override
	public RapportType addToTotalEngagement(RapportType totalEng) {
		String code  = totalEng.getCode().concat("").concat(totalEng.getTypes());
		totalEng.setCode(code);
		return rapportTypeRepository.save(totalEng);
	}

	@Override
	public PortefeuilleIndirect addtPortefeuilleIndirect(String type, String dateReport) {
		 DecimalFormat df = new DecimalFormat("0.00"); // import java.text.DecimalFormat;
		 
		 //decimal forma
		 Locale locale = new Locale("en", "UK");

		 DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		 symbols.setDecimalSeparator(',');
		 symbols.setGroupingSeparator(' ');

		 String pattern = "#,##0.##";
		 DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

	
        		
		Map<String, Double> result = new HashMap<String, Double>();
		
		List<RapportType> rapports = rapportTypeRepository.findByTypes(type);
		
		String columnToSum = "nominalExposure";
		PortefeuilleIndirect indirect = new PortefeuilleIndirect();
		
		for (RapportType rap : rapports) {

			JSONArray jsonArray = rap.getKeyValueMap();
			GenericSpesification<Engagement> genericSpesification = new GenericSpesification<Engagement>();
			jsonArray.forEach(json -> {
				JSONObject map = ((JSONObject) json);
				
				genericSpesification.add(new SearchCriteria(map));

			});
			columnToSum = rap.getSelectClause();
			result.put(rap.getCode(),
					engaggementrepositoryImpl.sum(genericSpesification, Double.class, columnToSum, dateReport));
			for (Map.Entry<String, Double> entry : result.entrySet()) {
				indirect.setTypes(type);
		System.out.println("type "   +type);
				if (("cautions".concat("").concat(type.replace(" ", ""))).equalsIgnoreCase(entry.getKey().replace(" ", ""))) {
				;
					 String number = decimalFormat.format(entry.getValue());
					
						indirect.setCautions(entry.getValue());
					
			
				
				} else if (("aval".concat("").concat(type.replace(" ", ""))).equalsIgnoreCase(entry.getKey().replace(" ", ""))) {
				
					 String number = decimalFormat.format(entry.getValue());
						indirect.setAval(entry.getValue());
				
				} else if (("obligationscautionnéesdedouane".concat("").concat(type.replace(" ", ""))).equalsIgnoreCase(entry.getKey().replace(" ", ""))) {
				
					
						
						indirect.setObligationCautionneeDuanee(entry.getValue());
						
				} else if (("ouverturedelettredecrédit".concat("").concat(type.replace(" ", ""))).equalsIgnoreCase(entry.getKey().replace(" ", ""))) {
					
					System.out.println("valueeeeee" + entry.getValue());
					indirect.setOuvertureLettreCredit(entry.getValue());
					}
				
			}

		}
		return indirect;
	}

	@Override
	public Map<String, Double> addtPortefeuilleDirect(String type, String dateReport) {
		Map<String, Double> result = new HashMap<String, Double>();
		List<RapportType> rapports = rapportTypeRepository.findByTypes(type);
		String columnToSum = "nominalExposure";
		for (RapportType rap : rapports) {
			JSONArray jsonArray = rap.getKeyValueMap();
			GenericSpesification<Engagement> genericSpesification = new GenericSpesification<Engagement>();
			jsonArray.forEach(json -> {
				JSONObject map = ((JSONObject) json);
				genericSpesification.add(new SearchCriteria(map));

			});
			columnToSum = rap.getSelectClause();
			result.put(rap.getCode(),
					engaggementrepositoryImpl.sum(genericSpesification, Double.class, columnToSum, dateReport));

		}

		return result;
	}

	@Override
	public List<String>  getAllRapport() {
		List<String> rapp =rapportTypeRepository.getTypes();
		return rapp;
	}

	@Override
	public List<String> getReportingDateBetween(String dateLast, Integer year) {
		 List<String>  mylist = new ArrayList<String>();
		 return mylist = clientRepository.findBetweenTowDate(dateLast, year);
		 
	}

}
