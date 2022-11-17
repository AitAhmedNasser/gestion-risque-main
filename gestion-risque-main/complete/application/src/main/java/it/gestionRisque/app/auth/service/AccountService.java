//package Auth.Service;
package it.gestionRisque.app.auth.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import it.gestionRisque.app.auth.entities.Agence;
import it.gestionRisque.app.auth.entities.Niveau;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;
@Service
public interface AccountService {

	User addNewUser(User user) throws Exception;
	List<User> ListUsers();
	User getUser(String username);
	void DeleteUser(Long id_user) throws Exception;
	User UpdateUser(Long id_user,User user) throws Exception;
	
	
	Role addNewRolle (Role role);
	List<Role> ListRoles() throws Exception;
	void DeleteRole(Long id_Role) throws Exception;
	Role UpdateRole(Long id_role, Role role) throws Exception;
	
	Agence AddAgence (Agence agence) throws Exception;
	List<Agence> AllAgence();
	void DeleteAgence(Long id_Agence) throws Exception;
	Agence UpdateAgence(Long id_agence, Agence agence) throws Exception;
	
	
	Niveau AddNiveau (Niveau niveau) throws Exception;
	List<Niveau> AllNiveau();
	void DeleteNiveau(Long id_Niveau) throws Exception;
//	Niveau UpdateNiveau();
}
