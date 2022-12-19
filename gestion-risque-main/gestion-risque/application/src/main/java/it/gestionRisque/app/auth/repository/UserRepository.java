//package Auth.Repository;
package it.gestionRisque.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long>{

	public User getUserByUsername(String username);
	public User getUserById(Long id_User);
}
