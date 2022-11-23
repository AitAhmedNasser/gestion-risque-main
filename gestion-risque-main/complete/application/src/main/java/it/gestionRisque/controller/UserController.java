//package Auth.Controller;
package it.gestionRisque.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.gestionRisque.app.auth.repository.AgenceRepository;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.repository.UserRepository;
import it.gestionRisque.app.auth.service.AccountService;
import it.gestionRisque.app.auth.entities.Agence;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private AccountService accountService ;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private AgenceRepository ageneceRepo;
	
	
	@GetMapping("/user")

	public ResponseEntity<List<User>> getUsers(){
		try {
			List<User> user = accountService.ListUsers();
			if (user.isEmpty()) {
		        return new ResponseEntity("Liste Users est null",HttpStatus.NO_CONTENT);
		      }else {
			
			return new ResponseEntity(user, HttpStatus.ACCEPTED);
		      }
			
		} catch (Exception e) {
			// TODO: handle exception
			 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
@GetMapping("/roles")
	public ResponseEntity<List<Role>> getRoles(){
	try {
		List<Role> role = accountService.ListRoles();
		if (role.isEmpty()) {
	        return new ResponseEntity("La liste des role est vide ",HttpStatus.NO_CONTENT);
	      }else {
		
		return new ResponseEntity(role, HttpStatus.ACCEPTED);
	      }
		
	} catch (Exception e) {
		// TODO: handle exception
		 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
			
	}
	
	 @RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public ResponseEntity<User> saveUser(@RequestBody User user){
		 try {
				accountService.addNewUser(user);
				return  new ResponseEntity(user, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	}
	
	@PostMapping("/roles/save")
	public ResponseEntity<Role> saveRoles(@RequestBody Role role){
		 try {
				accountService.addNewRolle(role);
				return  new ResponseEntity(role, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	}
	
	@PostMapping("/Agence/addAgence")
	public ResponseEntity<Agence>  AddAgence(@RequestBody Agence agence) throws Exception{
		 try {
				accountService.AddAgence(agence);
				return  new ResponseEntity(agence, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
	}
	
	@GetMapping("/Agences")
	public ResponseEntity<List<Agence>> AllAgence(){
		try {
			List<Agence> agences = accountService.AllAgence();
			if (agences.isEmpty()) {
		        return new ResponseEntity("Liste Agences est null",HttpStatus.NO_CONTENT);
		      }else {
			
			return new ResponseEntity(agences, HttpStatus.ACCEPTED);
		      }
			
		} catch (Exception e) {
			// TODO: handle exception
			 return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	@PutMapping("/user/update/{id_user}")
	@PreAuthorize("hasAnyAuthority('ConsulterRessources')")
	public ResponseEntity<User> UpdateUser(@PathVariable Long id_user, @RequestBody User user ){
	try {
		
		Optional<User> UserData = userRepo.findById(id_user);
		
		if(UserData.isPresent()) {
			return  new ResponseEntity<>(accountService.UpdateUser(id_user, user), HttpStatus.CREATED);
		}else {
			 return new ResponseEntity("l'utilisateur n'existe pas ",HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
	@PutMapping("/role/update/{id_role}")
	public ResponseEntity<User> UpdateUser(@PathVariable Long id_role, @RequestBody Role role ){
	try {
		
		Optional<Role> RoleData = roleRepo.findById(id_role);
		
		if(RoleData.isPresent()) {
			return  new ResponseEntity(accountService.UpdateRole(id_role, role), HttpStatus.CREATED);
		}else {
			 return new ResponseEntity("le role n'existe pas ",HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
	
	@PutMapping("/agence/update/{id_agence}")
	public ResponseEntity<Agence> UpdateAgence(@PathVariable Long id_agence, @RequestBody Agence agence ){
	try {
		
		Optional<Agence> AgenceData = ageneceRepo.findById(id_agence);
		
		if(AgenceData.isPresent()) {
			return  new ResponseEntity(accountService.UpdateAgence(id_agence, agence), HttpStatus.CREATED);
		}else {
			 return new ResponseEntity("cette Agence n'existe pas ",HttpStatus.NOT_FOUND);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
	
	@DeleteMapping("/user/delete/{id_user}")
	public ResponseEntity<?> DeleteUser(@PathVariable Long id_user){
		 try {
			accountService.DeleteUser(id_user);
			 return new ResponseEntity("User Deleted", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
			}
	}
	
	@DeleteMapping("/role/delete/{id_Role}")
	public ResponseEntity<?> DeleteRole(@PathVariable Long id_Role){
		 try {
			accountService.DeleteRole(id_Role);
			 return new ResponseEntity("Role Deleted", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/agence/delete/{id_Agence}")
	public ResponseEntity<?> DeleteAgence(@PathVariable Long id_Agence){
		 try {
			accountService.DeleteAgence(id_Agence);
			return new ResponseEntity("Agence Deleted", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
