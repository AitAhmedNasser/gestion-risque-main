package it.gestionRisque.app.di.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.Entities.Engagement;
import it.gestionRisque.app.Repositories.EngagementRepository;


@Service
public class EngagementsService {
	
	@Autowired
	EngagementRepository engagementRepository;
	
	public void saveAllEngagements(List<Engagement> engagements) {
		engagementRepository.saveAll(engagements);
	}
	
	public Engagement saveOneEngagement(Engagement engagement) {
		return  engagementRepository.save(engagement);
	}
}
