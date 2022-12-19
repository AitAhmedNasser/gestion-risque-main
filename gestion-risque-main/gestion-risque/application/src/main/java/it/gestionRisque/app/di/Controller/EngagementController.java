package it.gestionRisque.app.di.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repositories.EngagementRepository;



@RestController

@RequestMapping("engagement")
@CrossOrigin("*")
public class EngagementController {
	
	@Autowired
	EngagementRepository engagementRepository;

	@GetMapping("/")
	public List<Engagement> getEngagements(){
		return engagementRepository.findAll();
	}
}
