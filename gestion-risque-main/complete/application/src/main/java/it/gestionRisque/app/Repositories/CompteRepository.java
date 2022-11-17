package it.gestionRisque.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.Entities.Compte;
@RepositoryRestResource
public interface CompteRepository  extends JpaRepository<Compte, Long>{

    @Query(nativeQuery = true,value = "Select * from engagements e, clients c, compte cp Where e.id_client = c.id and c.id= cp.id_client ")
    public Compte findCompte();

}
