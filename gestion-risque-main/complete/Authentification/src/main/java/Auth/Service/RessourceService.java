package Auth.Service;

import java.security.Permission;
import java.util.List;
import java.util.Optional;

import Auth.entities.Permissions;
import Auth.entities.Privilege;
import Auth.entities.Ressource;
import Auth.entities.Role;

public interface RessourceService {
	
	
	
	Privilege createPrivilegeIfNotFound (Privilege Privilege_Name) throws Exception;
	List<Privilege> AllPrivilege();
	
	void PrermissionsToRoles(Long id_Roles, Long id_Permissions) throws Exception;
	void RemovePermissionToRole(Long id_Roles, Long id_Permissions)throws Exception;
	
	
	Ressource CreateRessource(Ressource ressource) throws Exception;
	List<Ressource> AllRessources();
	
	
	Permissions createPermissions(Permissions per) throws Exception;
	Permissions addPermissions(Long id_Privileges,Long id_Ressources) throws Exception;
	List<Permissions> AllPermissions();
	void DeletePermission(Long id_permission) throws Exception;
	Permissions UpdatePermission(Long id_Permission,Permissions permission) throws Exception;
}
