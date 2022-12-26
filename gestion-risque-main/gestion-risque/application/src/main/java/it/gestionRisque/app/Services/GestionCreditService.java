package it.gestionRisque.app.Services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Entities.RapportType;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import it.gestionRisque.app.Repporting.PortefeuilleIndirect;



public interface GestionCreditService {

	//client
Client	addToClient(Client client) ;
List<Client>getallClient();
ResponseEntity<Client> updateProduit(Client client);

List<String> getClientByDateRepo(Integer year);

CreditParticulier addToCredit(String dateREPO) throws ParseException;
CreditEntreprise addToCreditEntre(String dateREPO) throws ParseException;

//engagment
Engagement addToengagement(Engagement engagement);



//compte
Compte addTocompte(Compte compte);
List<Compte>getAllcompte();
List<String>  getAllRapport();
PortefeuilleIndirect addtPortefeuilleIndirect(String codeRapport , String dateReport);
Map<String,Double> addtPortefeuilleDirect(String codeRapport , String dateReport);
List<String> getReportingDateBetween (String dateLast, Integer year);
//add to Total Engagment 
RapportType addToTotalEngagement (RapportType totalEng);

}
