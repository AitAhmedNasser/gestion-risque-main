//package Authentification.Request;
package  it.gestionRisque.app.auth.request;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class AuthRequest {
	private String username;
	private String password;

}
