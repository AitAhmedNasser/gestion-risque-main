//package Auth.Service.ServiceImp;
package  it.gestionRisque.app.auth.service.ServiceImp;

import java.security.Permission;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import it.gestionRisque.app.auth.repository.PermissionRepo;
import it.gestionRisque.app.auth.repository.PrivilegeRepository;
import it.gestionRisque.app.auth.repository.RessourceRepository;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.service.RessourceService;
import it.gestionRisque.app.auth.entities.Permissions;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Ressource;
import it.gestionRisque.app.auth.entities.Role;

@Service
@Transactional
public class RessourceServiceImp implements RessourceService {

	public RessourceServiceImp(RessourceRepository ressourcesRepo, PrivilegeRepository privilegeRepo,
			RoleRepository roleRepo) {
		super();
		this.ressourcesRepo = ressourcesRepo;
		this.privilegeRepo = privilegeRepo;
		this.roleRepo = roleRepo;
	}

	private RessourceRepository ressourcesRepo;

	private PrivilegeRepository privilegeRepo;

	private RoleRepository roleRepo;

	@Autowired
	private PermissionRepo PermRepo;
	// Create a new privilege

	@Override
	public Privilege createPrivilegeIfNotFound(Privilege privilege) throws Exception {
		Privilege privilegeByName = privilegeRepo.getPrivilegeBynameP(privilege.getNameP());

		if (privilegeByName != null) {
			throw new Exception("Le privilege dans le nom  " + privilege.getNameP() + "Existe deja creer un autre");
		} else {
			// TODO Auto-generated method stub
			return privilegeRepo.save(privilege);
		}

	}

	// Create a new ressource

	@Override
	public Ressource CreateRessource(Ressource ressource) {

		// TODO Auto-generated method stub
		return ressourcesRepo.save(ressource);

	}

	@Override
	public List<Privilege> AllPrivilege() {
		// TODO Auto-generated method stub
		return privilegeRepo.findAll();
	}

	@Override
	public List<Ressource> AllRessources() {
		// TODO Auto-generated method stub
		return ressourcesRepo.findAll();
	}

	public List<Permissions> AllPermissions() {
		return PermRepo.findAll();
	}

	@Override
	public Permissions addPermissions(Long id_Privileges, Long id_Ressources) throws Exception {
		// TODO Auto-generated method stub
		Privilege previlege = privilegeRepo.findById(id_Privileges).get();
		Ressource ressource = ressourcesRepo.findById(id_Ressources).get();

		List<Permissions> Listper = PermRepo.permissionWhereId(id_Privileges, id_Ressources);

		if (previlege == null || ressource == null) {
			throw new RuntimeException("le privilege ou la resssource n'existe pas ");

		} else {

			Listper.forEach(p -> {
				if ((p.getPrivileges().getId() == id_Privileges) && (p.getRessources().getId() == id_Ressources)) {

					throw new RuntimeException("la Permission existe deja dans la base de données");

				}
			});
			Permissions Permissions = new Permissions(previlege, ressource,
					previlege.getNameP().toString() + ressource.getName().toString());

			return PermRepo.save(Permissions);
		}

	}

	@Override
	public Permissions createPermissions(Permissions permission) throws RuntimeException {
		// TODO Auto-generated method stub
		Privilege previlege = privilegeRepo.findById(permission.getPrivileges().getId()).get();
		Ressource ressource = ressourcesRepo.findById(permission.getRessources().getId()).get();
		List<Permissions> ListPerm = PermRepo.findAll();

		if (previlege == null || ressource == null) {
			throw new RuntimeException("le privilege ou la resssource n'existe pas ");

		} else {

			ListPerm.forEach(p -> {
				if ((p.getPrivileges().getId() == permission.getPrivileges().getId())
						&& (p.getRessources().getId() == permission.getRessources().getId())) {

					throw new RuntimeException("la Permission existe deja dans la base de données");

				}
			});

			Permissions Permissions = new Permissions(previlege, ressource,
					previlege.getNameP().toString() + ressource.getName().toString());

			return PermRepo.save(Permissions);
		}

	}

	@Override
	public void PrermissionsToRoles(Long id_Roles, Long id_Permissions) throws Exception {
		// TODO Auto-generated method stub
		Role role = roleRepo.getRoleById(id_Roles);
		Permissions permissions = PermRepo.getPermissionsById(id_Permissions);

		if (role == null || permissions == null) {
			throw new Exception("role ou permission n'existe pas");
		} else {

			role.getPermissions().add(permissions);
		}

	}

	@Override
	public void DeletePermission(Long id_permission) throws Exception {
		// TODO Auto-generated method stub
		Permissions permission = PermRepo.getPermissionsById(id_permission);

		if (permission == null) {

			throw new Exception("La permission n'existe pas");
		} else {
			PermRepo.delete(permission);
		}
	}

	@Override
	public void RemovePermissionToRole(Long id_Roles, Long id_Permissions) throws Exception {
		// TODO Auto-generated method stub
		Role role = roleRepo.getRoleById(id_Roles);
		Permissions permissions = PermRepo.getPermissionsById(id_Permissions);

		Map<Long, Long> ListPerm = roleRepo.roleHasPermission(id_Roles, id_Permissions);

		if (role == null || permissions == null) {
			throw new Exception("role ou permission n'existe pas");

		} else {

			if (this.roleHasPermission(id_Roles, id_Permissions)) {
				throw new Exception("Ce role ne contient pas cette permission");
			}
			role.getPermissions().remove(permissions);
		}

	}

	@Override
	public Permissions UpdatePermission(Long id_Permission, Permissions permission) throws Exception {
		// TODO Auto-generated method stub
		Permissions permissions = PermRepo.findById(id_Permission).get();
		List<Permissions> ListPerm = PermRepo.findAll();

		Privilege previlege = privilegeRepo.findById(permission.getPrivileges().getId()).get();
		Ressource ressource = ressourcesRepo.findById(permission.getRessources().getId()).get();

		if (permissions == null) {
			throw new Exception("Permission dans id  " + id_Permission + " not existe");
		} else {

			ListPerm.forEach(p -> {
				if ((p.getPrivileges().getId() == permission.getPrivileges().getId())
						&& (p.getRessources().getId() == permission.getRessources().getId())) {

					throw new RuntimeException("la Permission existe deja dans la base de données");

				}
			});

			permissions.setNamepermission(previlege.getNameP().toString() + ressource.getName().toString());

			permissions.setPrivileges(permission.getPrivileges());
			permissions.getPrivileges().setNameP(previlege.getNameP());
			permissions.getPrivileges().setDescription(previlege.getDescription());

			permissions.setRessources(permission.getRessources());
			permissions.getRessources().setName(ressource.getName());
			permissions.getRessources().setDescription(ressource.getDescription());

			return PermRepo.save(permissions);
		}

	}

	protected boolean roleHasPermission(Long roleId, Long permissionId) throws Exception {
		Map<Long, Long> ListPerm = roleRepo.roleHasPermission(roleId, permissionId);
		return ListPerm.isEmpty();
	}

}
