package it.gestionRisque.app.Controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import it.gestionRisque.app.Entities.Client;
import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repporting.CreditEntreprise;
import it.gestionRisque.app.Repporting.CreditParticulier;
import it.gestionRisque.app.Services.GestionCreditService;
import it.gestionRisque.app.Services.RepportServices;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
@RestController
@AllArgsConstructor
@CrossOrigin("*")

public class MainController {
	
	private GestionCreditService gestionCreditservices ;
	private RepportServices creditService;
	
	@PostMapping("/saveClient")
	 public Client saveClient(@RequestBody Client client)  {
		 return gestionCreditservices.addToClient(client);
	 }

	
	@GetMapping("/allClient")
	public List<Client> getClient(){
		List<Client> listClient= new ArrayList();
		listClient= gestionCreditservices.getallClient();
		return listClient;
	}
	
	
	
	//engagment
	
	@PostMapping("/saveEngagement")
	public Engagement saveEngagement(@RequestBody Engagement engagement) {
		return gestionCreditservices.addToengagement(engagement);
	}
	


	@PostMapping("saveCompte")
	public Compte saveComte(@RequestBody Compte compte) {
		return gestionCreditservices.addTocompte(compte);
	}
	@GetMapping("/engagementClient")
	public void angagementClient(){
		
	}
	
	@GetMapping("/creditParticulier/{dateRepos}")
	public CreditParticulier showcredit(@PathVariable("dateRepos")  String dateRepo) throws ParseException  {
	   // Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo); 
		//LocalDate localDate = LocalDate.parse(dateRepo);
	CreditParticulier listCreditPart = gestionCreditservices.addToCredit(dateRepo);
		return  listCreditPart;
	}
	
	
	@GetMapping("/creditEntreprise/{dateRepos}")
	public CreditEntreprise showcreditEntre(@PathVariable("dateRepos")  String dateRepo) throws ParseException  {
		System.out.println("daterep " +dateRepo);
		// Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateRepo); 

		CreditEntreprise listCreditEntre= gestionCreditservices.addToCreditEntre(dateRepo);
		return listCreditEntre;
	}
	
	
	
	@GetMapping("/allReportDate/{year}")
	public List<String> getAllDateRepo(@PathVariable("year")  Integer year){
		List<String> mylist =gestionCreditservices.getClientByDateRepo(year); 
	
		System.out.println("date d " +mylist);
		 return mylist;
	}
	
	
	@PostMapping(path="/pdf")
	public ResponseEntity<byte[]> getJob(@RequestBody String json) throws StreamReadException, DatabindException, MalformedURLException, IOException, JRException, ParseException {	   
	return creditService.createReporting(json);
  }

}
