//package Auth.Controller;
package it.gestionRisque.controller;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.gestionRisque.app.auth.repository.PermissionRepo;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.service.AccountService;
import it.gestionRisque.app.auth.service.RessourceService;
import it.gestionRisque.app.auth.service.ServiceImp.RessourceServiceImp;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Permissions;
import it.gestionRisque.app.auth.entities.Ressource;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;
import lombok.Data;

@RestController
@RequestMapping("/api")
public class RessourceController {

	@Autowired
	private RessourceService ressService;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PermissionRepo permissionRepo;
	
//	@PostMapping("/permissions/add")
//	public ResponseEntity<Permissions> addPermissions(Long id_Privileges, Long id_Ressources){
//		try {
//			Permissions per = ressService.addPermissions(id_Privileges, id_Ressources);
//			return  new ResponseEntity<>(per, HttpStatus.CREATED);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}
//	
	@PostMapping("/permissions/save")
	public ResponseEntity<Permissions> savePermissions(@RequestBody Permissions permissions){
		try {
			Permissions per = ressService.createPermissions(permissions);
			return  new ResponseEntity<>(per, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
//	
//	@RequestMapping(value = "/permissions/addPermissionsToRole",method = RequestMethod.POST)
//	public ResponseEntity<?> addPermissionsToRoles(Long id_Roles, Long id_Permissions){
//		Role role = roleRepo.getRoleById(id_Roles);
//		Permissions permission = permissionRepo.getPermissionsById(id_Permissions);
//		try {
//			ressService.PrermissionsToRoles(id_Roles, id_Permissions);
//			return  new ResponseEntity("la permission ' "+permission.getNamepermission()+" ' est affecter a un role ' "+role.getName()+" '", HttpStatus.CREATED);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//		}
//	}
//
//	@RequestMapping(value = "/permissions/RemovePermissionsToRole",method = RequestMethod.POST)
//	public ResponseEntity<?> RemovePermissionToRole(Long id_Roles, Long id_Permissions){
//		Role role = roleRepo.getRoleById(id_Roles);
//		Permissions permission = permissionRepo.getPermissionsById(id_Permissions);
//		
//		try {
//			ressService.RemovePermissionToRole(id_Roles, id_Permissions);
//			return  new ResponseEntity("la permission ' "+permission.getNamepermission()+" ' est disactiver pour le role  ' "+role.getName()+" '", HttpStatus.ACCEPTED);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//		
//		}
//	}


	@GetMapping("/ressources")
	public ResponseEntity<List<Ressource>> AllRessources(){
		
		try {
			List<Ressource> ressources = ressService.AllRessources();
			if (ressources.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }else {
			
			return new ResponseEntity(ressources, HttpStatus.ACCEPTED);
		      }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	@GetMapping("/privileges")
	public ResponseEntity<List<Privilege>> AllPrivileges(){
		try {
			List<Privilege> privilege = ressService.AllPrivilege();
			if (privilege.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }else {
			
			return new ResponseEntity(privilege, HttpStatus.ACCEPTED);
		      }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@GetMapping("/permissions")
	public ResponseEntity<List<Permissions>> AllPermissions(){
		try {
			List<Permissions> permissions = ressService.AllPermissions();
			if (permissions.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }else {
			
			return new ResponseEntity(permissions, HttpStatus.ACCEPTED);
		      }
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	@DeleteMapping("/permissions/delete/{permission_id}")
	public ResponseEntity<?> DeletePermission(@PathVariable Long permission_id){
		
		try {
			ressService.DeletePermission(permission_id);
			 return new ResponseEntity("Permission Deleted", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
		
	@PutMapping("permissions/update/{id_Permission}")
		public ResponseEntity<Permissions> UpdatePermission(@PathVariable Long id_Permission, @RequestBody Permissions permission ){
		try {
			
			Optional<Permissions> PermissionData = permissionRepo.findById(id_Permission);
			
			if(PermissionData.isPresent()) {
				return  new ResponseEntity<>(ressService.UpdatePermission(id_Permission, permission), HttpStatus.CREATED);
			}else {
				 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	}
	
