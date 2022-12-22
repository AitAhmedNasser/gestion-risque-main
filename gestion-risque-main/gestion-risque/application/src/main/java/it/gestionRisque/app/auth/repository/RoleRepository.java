//package Auth.Repository;
package it.gestionRisque.app.auth.repository;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import it.gestionRisque.app.auth.entities.Role;


@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {
;
	public Role getRolesByName(String name);
	
	public Role getRoleById(Long id_Roles);
	
	@Query(value = "SELECT * FROM roles_permissions", nativeQuery = true)
	public Map<Long,Long> RolPermission();
	
	@Query(value = "SELECT * FROM roles_permissions WHERE role_id=?1 AND permissions_id=?2", nativeQuery = true)
	public Map<Long,Long> roleHasPermission(Long roleId, Long permissionId);
}
