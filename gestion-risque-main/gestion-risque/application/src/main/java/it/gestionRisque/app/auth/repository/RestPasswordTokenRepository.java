package it.gestionRisque.app.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.PasswordResetToken;


@RepositoryRestResource
public interface RestPasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	@Query(value = "SELECT * FROM password_reset_token WHERE token = ?1 AND expiry_date > NOW() AND used = false ", nativeQuery = true)
	public PasswordResetToken checkUserToken(String token);
	
	public PasswordResetToken findByToken(String token);
	
	
	
	public PasswordResetToken getByToken(String token);

}
