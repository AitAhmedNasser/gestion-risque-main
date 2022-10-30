package Auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import Auth.entities.User;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long>{

	public User getUserByUsername(String username);
	public User getUserById(Long id_User);
}
