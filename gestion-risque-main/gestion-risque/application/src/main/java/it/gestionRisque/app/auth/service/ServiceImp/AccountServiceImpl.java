// package Auth.Service.ServiceImp;
package  it.gestionRisque.app.auth.service.ServiceImp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import it.gestionRisque.app.auth.repository.AgenceRepository;
import it.gestionRisque.app.auth.repository.PermissionRepo;
import it.gestionRisque.app.auth.repository.PrivilegeRepository;
import it.gestionRisque.app.auth.repository.RestPasswordTokenRepository;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.repository.UserRepository;
import it.gestionRisque.app.auth.repository.niveauRepository;
import it.gestionRisque.app.auth.service.AccountService;
import it.gestionRisque.app.auth.entities.Agence;
import it.gestionRisque.app.auth.entities.Niveau;
import it.gestionRisque.app.auth.entities.Permissions;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Ressource;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
public class AccountServiceImpl  implements AccountService{

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PermissionRepo permissionsRepo;
		
	@Autowired
	private PasswordEncoder  passwordEncoder;
	
	@Autowired
	private niveauRepository niveauRepo;
	
	@Autowired
	private AgenceRepository agenceRepo;
	@Autowired
	private RestPasswordTokenRepository PasswordRestTokenRepo;
	
	
	public AccountServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
		super();
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}




	@Override
	public User addNewUser(User user) throws Exception{
		Optional<Role> role = roleRepository.findById(user.getRoles().getId());
		User userbyUsername = userRepository.getUserByUsername(user.getUsername());
		if(role == null ) {
			throw new Exception("Ce Role n'existe pas ");
		}else {
			if(userbyUsername !=null) {
				throw new Exception("Cette utilisiteur existe deja  ");
			}
			// TODO Auto-generated method stub
			String Pws  =user.getPassword();
			user.setPassword(passwordEncoder.encode(Pws));
			return userRepository.save(user);
		}
	}

	@Override
	public Role addNewRolle(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public Role createRole(Role roole) throws Exception {
		// TODO Auto-generated method stub
		
		 Set permissions ;
		 Set<Permissions> permmissionnsList = new HashSet<>(); 
		 
		 Niveau niveaux = niveauRepo.findNiveauByIdNiv(roole.getNiveaux().getIdNiv());
		 
		 			permissions= roole.getPermissions().stream().map((permission -> {
		 			Permissions perm = permissionsRepo.getPermissionsById(permission.getId());
		 			permmissionnsList.add(perm);
		 			return permission;
			
       })).collect(Collectors.toSet());

		 		
		 Role role = new Role(null,roole.getName(),niveaux,null,permmissionnsList);

		
		 
		return roleRepository.save(role);
	}




	@Override
	public Agence AddAgence(Agence agence) throws Exception {
		// TODO Auto-generated method stub
		Agence agenceName = agenceRepo.findByAgenceName(agence.getAgenceName());
		if(agenceName != null ) {
			throw new Exception("L'agence existe deja");
		}else {
			return agenceRepo.save(agence);
		}
		
	}



	@Override
	public List<User> ListUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}


	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userRepository.getUserByUsername(username);
	}


	@Override
	public User getUserByMail(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.getUserByEmail(userEmail);
	}




	@Override
	public List<Role> ListRoles() throws Exception {
		// TODO Auto-generated method stub
		List<Role>   role = roleRepository.findAll();
		if(role == null ) {
			throw new Exception("La liste des roles est vide ");
			
		}
		return roleRepository.findAll();
	}




	@Override
	public List<Agence> AllAgence() {
		// TODO Auto-generated method stub
		return agenceRepo.findAll();
	}




	@Override
	public void DeleteUser(Long id_user) throws Exception{
		// TODO Auto-generated method stub
		User user = userRepository.getUserById(id_user);
		
		if(user ==null) {
			throw new Exception("Utilisateur not found ");
		}else {
			userRepository.deleteById(id_user);
		}
		
	}




	@Override
	public void DeleteRole(Long id_Role) throws Exception {
		// TODO Auto-generated method stub
	Role role = roleRepository.getRoleById(id_Role);
	List<User>ListUsersRole = role.getUsers();
		if(role ==null) {
			throw new Exception("Role not found ");
		}else {
			roleRepository.deleteById(id_Role);
		}
		
	}
		
	@Override
	public void DeleteAgence(Long id_Agence) throws Exception {
		// TODO Auto-generated method stub
		Agence agence = agenceRepo.getAgenceById(id_Agence);
		
		if(agence ==null) {
			throw new Exception("Agence is  not found ");
		}else {
			agenceRepo.deleteById(id_Agence);
		}
	}




	@Override
	public Niveau AddNiveau(Niveau niveau) throws Exception {
		// TODO Auto-generated method stub
		return niveauRepo.save(niveau);
	}




	@Override
	public List<Niveau> AllNiveau() {
		// TODO Auto-generated method stub
		return niveauRepo.findAll();
	}




	@Override
	public void DeleteNiveau(Long id_Niveau) throws Exception {
		// TODO Auto-generated method stub
		Niveau niveau = niveauRepo.findNiveauByIdNiv(id_Niveau);
		
		if(niveau ==null) {
			throw new Exception("Niveau not found ");
		}else {
			niveauRepo.deleteById(id_Niveau);
		}
	}




	@Override
	public User UpdateUser(Long id_user, User user) throws  Exception{
		// TODO Auto-generated method stub
		User users = userRepository.findById(id_user).get();
		Role role = roleRepository.getRoleById(user.getRoles().getId());
		
		Agence agence = agenceRepo.getAgenceById(user.getAgence().getId());
		
		if(users == null) {
			throw new Exception("L'utilisateur dans id  "+id_user+" not existe");
		}
		else {
			if(role == null || agence == null) {
				throw new Exception("le role ou l'agence n'existe pas ");
			}
//		
			users.setNom(user.getNom());
			users.setPrenom(user.getPrenom());
			users.setEmail(user.getEmail());
			users.setRoles(role);
			users.getRoles().setName(role.getName());
			users.getRoles().setNiveaux(role.getNiveaux());
			users.getRoles().setPermissions(role.getPermissions());
			users.setAgence(agence);
			users.getAgence().setAgenceName(agence.getAgenceName());
			users.getAgence().setDescription(agence.getDescription());
//			
			return userRepository.save(users);
		}
		
	}

//Update Password OfUser
	@Override
	public User UpdatePasswordUser(String Password, Long UserId) {
		// TODO Auto-generated method stub
		User user = userRepository.getUserById(UserId);
		
		user.setPassword(passwordEncoder.encode(Password));
		return userRepository.save(user);
		
	}


	@Override
	public Role UpdateRole(Long id_role, Role role) throws Exception {
		// TODO Auto-generated method stub
		Role roleData = roleRepository.findById(id_role).get();
		Niveau niv = niveauRepo.findNiveauByIdNiv(role.getNiveaux().getIdNiv()); 
		
		 Set permissions ;
		 Set<Permissions> permmissionnsList = new HashSet<>(); 

		 			permissions= role.getPermissions().stream().map((permission -> {
		 			Permissions perm = permissionsRepo.getPermissionsById(permission.getId());
		 			permmissionnsList.add(perm);
		 			return permission;
      })).collect(Collectors.toSet());

		 			
		if(roleData ==  null ) {
			throw new Exception("Le role n'existe pas dans la base de donnée");
			
		}else {
			roleData.setName(role.getName());
			roleData.setNiveaux(niv);
			roleData.setPermissions(permmissionnsList);
			return roleRepository.save(roleData);
			
		}
		
	}




	@Override
	public Agence UpdateAgence(Long id_agence, Agence agence) throws Exception {
		// TODO Auto-generated method stub
		
	Agence agenceData = agenceRepo.findById(id_agence).get();
		
				if(agenceData ==  null ) {
					throw new Exception("L'agence n'existe pas dans la base de donnée");
					
				}
			if(agenceData.getAgenceName() == agence.getAgenceName() || agenceData.getAgenceName() == agence.getAgenceName()  )
			{
				throw new Exception("L'agence existe déja dans la base de donnée");
			}
				
				agenceData.setAgenceName(agence.getAgenceName());
				agenceData.setDescription(agence.getDescription());
				return agenceRepo.save(agenceData);
			}
				
			
	
	




	@Override
	public User getUserById(Long id_user) throws Exception {
		
		// TODO Auto-generated method stub
		User user = userRepository.findById(id_user).get();
		if(user == null ) {
			throw new Exception("L'Utilisateur n'existe pas ");
		}else {
			
			return userRepository.findById(id_user).get();
		}
	}




	@Override
	public Role getRoleById(Long id_role) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id_role).get();
	}




	@Override
	public Agence getAgenceByID(Long id_Agence) {
		// TODO Auto-generated method stub
		return agenceRepo.findById(id_Agence).get();
	}




	@Override
	public Niveau getNiveauById(Long id_Niv) {
		// TODO Auto-generated method stub
		return niveauRepo.findNiveauByIdNiv(id_Niv);
	}




	@Override
	public Niveau UpdateNiveau(Long niv_id,Niveau niveau ) throws Exception {
		// TODO Auto-generated method stub
		Niveau niv = niveauRepo.findNiveauByIdNiv(niv_id);
		
		if(niv ==  null ) {
			throw new Exception("Le niveau n'existe pas dans la base de donnée");
			
		}else {
			niv.setNameNiveau(niveau.getNameNiveau());
			niv.setNiveauNumber(niveau.getNiveauNumber());
			return niveauRepo.save(niv);
			
		}
	}


	
}
