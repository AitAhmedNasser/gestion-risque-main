package Auth.Service;

import java.util.Collection;
import java.util.List;

import Auth.entities.Agence;
import Auth.entities.Niveau;
import Auth.entities.Privilege;
import Auth.entities.Role;
import Auth.entities.User;

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
