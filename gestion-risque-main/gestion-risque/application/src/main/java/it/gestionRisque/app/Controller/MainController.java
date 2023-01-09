package it.gestionRisque.app.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Entities.RapportType;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import it.gestionRisque.app.Repporting.PortefeuilleIndirect;
import it.gestionRisque.app.Services.GestionCreditService;
import it.gestionRisque.app.Services.RepportServices;
import it.gestionRisque.app.di.Services.ClientService;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;

@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class MainController {

	private GestionCreditService gestionCreditservices;
	private RepportServices creditService;

	private ClientService clientService;

	@PostMapping("/saveClient")
	public Client saveClient(@RequestBody Client client) {
		return gestionCreditservices.addToClient(client);
	}

	@GetMapping("/allClient")
	public List<Client> getClient() {
		List<Client> listClient = new ArrayList();
		listClient = gestionCreditservices.getallClient();
		return listClient;
	}

	// Engagement

	@PostMapping("/saveEngagement")
	public Engagement saveEngagement(@RequestBody Engagement engagement) {
		return gestionCreditservices.addToengagement(engagement);
	}

	@PostMapping("saveCompte")
	public Compte saveComte(@RequestBody Compte compte) {
		return gestionCreditservices.addTocompte(compte);
	}

	@GetMapping("/engagementClient")
	public void angagementClient() {

	}

	@GetMapping("/creditParticulier/{dateRepos}")
	public CreditParticulier showcredit(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);
		// LocalDate localDate = LocalDate.parse(dateRepo);
		CreditParticulier listCreditPart = gestionCreditservices.addToCredit(dateRepo);
		return listCreditPart;
	}

	@GetMapping("/creditEntreprise/{dateRepos}")
	public CreditEntreprise showcreditEntre(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		System.out.println("daterep " + dateRepo);
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);

		CreditEntreprise listCreditEntre = gestionCreditservices.addToCreditEntre(dateRepo);
		return listCreditEntre;
	}

	@GetMapping("/allReportDate/{year}")
	public List<String> getAllDateRepo(@PathVariable("year") Integer year) {
		List<String> mylist = gestionCreditservices.getClientByDateRepo(year);

		// System.out.println("date d " +mylist);
		return mylist;
	}

	@PostMapping(path = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getJob(@RequestBody String json) throws StreamReadException, DatabindException,
			MalformedURLException, IOException, JRException, ParseException {
		return creditService.createReporting(json);
	}

	// portfeuille indirect

	@GetMapping(path = "/portfeuilleIndirect/{codeRapport}/{dateRepos}")
	public PortefeuilleIndirect getTotalEngagement(@PathVariable String codeRapport,
			@PathVariable("dateRepos") String dateRepo) {
		return gestionCreditservices.addtPortefeuilleIndirect(codeRapport, dateRepo);

	}

	@PostMapping("/addToTotal")
	public RapportType saveTotalEn(@RequestBody RapportType totalEng) {
		return gestionCreditservices.addToTotalEngagement(totalEng);
	}

	@GetMapping(path = "/allRapportType")
	public List<String> getAllRapport() {
		return gestionCreditservices.getAllRapport();
	}

	@GetMapping(path = "/repportBetween/{dateRepo}/{year}")
	public List<String> getReportBeween(@PathVariable("dateRepo") String lastdate, @PathVariable("year") Integer year) {
		return gestionCreditservices.getReportingDateBetween(lastdate, year);
	}

	@GetMapping("/findGroupedBySecteur/{dateRepos}")
	public List<String[]> findGroupedBySecteur(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);
		// LocalDate localDate = LocalDate.parse(dateRepo);
		List<String[]> listSecteurs = clientService.findBySecteur(dateRepo);
		return listSecteurs;
	}

	@GetMapping("/findTotalGroupedBySecteur/{dateRepos}")
	public List<String[]> findTotalGroupedBySecteur(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);
		// LocalDate localDate = LocalDate.parse(dateRepo);
		List<String[]> totalParSecteurs = clientService.findTotalBySecteur(dateRepo);
		return totalParSecteurs;
	}

	@GetMapping("/findGroupedByZone/{dateRepos}")
	public List<String[]> findGroupedByZone(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);
		// LocalDate localDate = LocalDate.parse(dateRepo);
		List<String[]> listSecteurs = clientService.findByZone(dateRepo);
		return listSecteurs;
	}

	@GetMapping("/findTotalGroupedByZone/{dateRepos}")
	public List<String[]> findTotalGroupedByZone(@PathVariable("dateRepos") String dateRepo) throws ParseException {
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo);
		// LocalDate localDate = LocalDate.parse(dateRepo);
		List<String[]> totalParSecteurs = clientService.findTotalByZone(dateRepo);
		return totalParSecteurs;
	}

}
