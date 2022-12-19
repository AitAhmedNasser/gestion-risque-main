//package Authentification.Request;
package  it.gestionRisque.app.auth.request;
import java.util.List;

import org.springframework.stereotype.Component;

import it.gestionRisque.app.auth.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter  @Setter @Data @Component
@NoArgsConstructor
public class JwtResponse {

	//List<String> roles


	public JwtResponse(String jwtAccessTocken,Role roles, String Nom,String prenom ,String email, List<String> permissions) {
		super();
		this.jwtAccessTocken = jwtAccessTocken;
		this.roles=roles;
		this.Nom = Nom;
		this.prenom=prenom;
		this.email=email;
		this.permissions = permissions;
	}
	private String jwtAccessTocken;
	private String Nom;
	private String prenom;
	private Role roles;
	private String email;
	private List<String> permissions;



}