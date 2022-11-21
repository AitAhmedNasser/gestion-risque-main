package it.gestionRisque.app.di.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.Entities.Compte;
import it.gestionRisque.app.Repositories.CompteRepository;


@Service
public class CompteService {
	
	@Autowired
	CompteRepository compteRepository;
	public void saveAllCompte (List<Compte> compteList) {
		compteRepository.saveAll(compteList);
	}

}
