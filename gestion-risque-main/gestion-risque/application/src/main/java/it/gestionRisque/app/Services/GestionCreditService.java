package it.gestionRisque.app.Services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import ar.com.fdvs.dj.domain.Style;
import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repporting.AnalyseProtefeuilleIndirect;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import net.sf.jasperreports.engine.JasperPrint;



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
AnalyseProtefeuilleIndirect addtPortefeuilleIndirect(String dateRepor);

//report 

}
