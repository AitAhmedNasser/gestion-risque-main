//package Auth.Repository;
package it.gestionRisque.app.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.Permissions;

@RepositoryRestResource
public interface PermissionRepo extends JpaRepository<Permissions, Long>{

//	SELECT privilegename,nom_res FROM permissions p, privilege pr, ressource rc WHERE p.privileges_id=pr.id And p.ressources_id=rc.id;

	public  Permissions  findPermissionsBynamepermission(String permissionName);
	public  Permissions getPermissionsById(Long id_Permissions);
	
	@Query(value = "SELECT * FROM permissions per WHERE per.privileges_id=?1 AND per.ressources_id=?2",nativeQuery = true )
	public List<Permissions> permissionWhereId(Long id_Privileges, Long id_Ressource);

	@Query(value = "@Query(value = \"SELECT * FROM permissions per WHERE per.privileges_id=?1 AND per.ressources_id=?2\",nativeQuery = true ",nativeQuery = true )
	public List<Permissions> roleHasNotPermissions(long id_Role);
	
}
