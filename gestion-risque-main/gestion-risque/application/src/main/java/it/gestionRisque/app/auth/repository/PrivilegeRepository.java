//package Auth.Repository;
package it.gestionRisque.app.auth.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Ressource;

@RepositoryRestResource
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>  {

	public Privilege getPrivilegeBynameP(String nameP);

	public Privilege getPrivilegeById(Long id_Privileges);

}
