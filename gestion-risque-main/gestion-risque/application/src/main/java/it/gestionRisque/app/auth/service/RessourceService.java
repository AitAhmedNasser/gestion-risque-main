//package Auth.Service;
package  it.gestionRisque.app.auth.service;

import java.security.Permission;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.gestionRisque.app.auth.entities.Permissions;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Ressource;
import it.gestionRisque.app.auth.entities.Role;

@Service
public interface RessourceService {
	
	
	
	Privilege createPrivilegeIfNotFound (Privilege Privilege_Name) throws Exception;
	List<Privilege> AllPrivilege();
	
	void PrermissionsToRoles(Long id_Roles, Long id_Permissions) throws Exception;
	
	
	Ressource CreateRessource(Ressource ressource) throws Exception;
	List<Ressource> AllRessources();
	
	
	Permissions createPermissions(Permissions per) throws Exception;
	List<Permissions> AllPermissions();
	void DeletePermission(Long id_permission) throws Exception;
	Permissions UpdatePermission(Long id_Permission,Permissions permission) throws Exception;
}
