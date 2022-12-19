//package Auth.Repository;
package it.gestionRisque.app.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import  it.gestionRisque.app.auth.entities.Agence;
import  it.gestionRisque.app.auth.entities.Niveau;

@RepositoryRestResource
public interface AgenceRepository extends JpaRepository<Agence, Long>{

	public Agence findByAgenceName(String agenceName);

	public Agence getAgenceById(Long id_Agence);

}
