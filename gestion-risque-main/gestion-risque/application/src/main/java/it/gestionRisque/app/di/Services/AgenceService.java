package it.gestionRisque.app.di.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gestionRisque.app.auth.entities.Agence;
import it.gestionRisque.app.auth.repository.AgenceRepository;

@Service
public class AgenceService {
	@Autowired
	AgenceRepository agenceRepository;

	@Transactional
	public void saveAllAgences(List<Agence> agences) {
		agenceRepository.saveAll(agences);
		
	}
}
