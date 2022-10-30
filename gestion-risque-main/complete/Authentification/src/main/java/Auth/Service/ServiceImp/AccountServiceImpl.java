 package Auth.Service.ServiceImp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

import Auth.Repository.AgenceRepository;
import Auth.Repository.PrivilegeRepository;
import Auth.Repository.RoleRepository;
import Auth.Repository.UserRepository;
import Auth.Repository.niveauRepository;
import Auth.Service.AccountService;
import Auth.entities.Agence;
import Auth.entities.Niveau;
import Auth.entities.Privilege;
import Auth.entities.Role;
import Auth.entities.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl  implements AccountService{

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder  passwordEncoder;
	
	@Autowired
	private niveauRepository niveauRepo;
	
	@Autowired
	private AgenceRepository agenceRepo;
	
	public AccountServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
		super();
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}




	@Override
	public User addNewUser(User user) throws Exception{
		Optional<Role> role = roleRepository.findById(user.getRoles().getId());
		if(role == null ) {
			throw new Exception("Ce Role n'existe pas ");
		}else {
			
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
		}else {
			if(role == null || agence == null) {
				throw new Exception("le role ou l'agence n'existe pas ");
			}
			users.setNom(user.getNom());
			users.setPrenom(user.getPrenom());
			users.setEmail(user.getEmail());
			users.setPassword(users.getPassword());
			user.setRoles(role);
			user.getRoles().setName(role.getName());
			user.getRoles().setNiveaux(role.getNiveaux());
			user.getRoles().setPermissions(role.getPermissions());
			users.setAgence(agence);
			user.getAgence().setAgenceName(agence.getAgenceName());
			user.getAgence().setDescription(agence.getDescription());
			
			return userRepository.save(users);
		}
		
	}




	@Override
	public Role UpdateRole(Long id_role, Role role) throws Exception {
		// TODO Auto-generated method stub
		Role roleData = roleRepository.findById(id_role).get();
		
		if(roleData ==  null ) {
			throw new Exception("Le role n'existe pas dans la base de donnée");
			
		}else {
			roleData.setName(role.getName());
			return roleRepository.save(roleData);
			
		}
		
	}




	@Override
	public Agence UpdateAgence(Long id_agence, Agence agence) throws Exception {
		// TODO Auto-generated method stub
		
	Agence agenceData = agenceRepo.findById(id_agence).get();
		
		if(agenceData ==  null ) {
			throw new Exception("L'agence n'existe pas dans la base de donnée");
			
		}else {
			agenceData.setAgenceName(agenceData.getAgenceName());
			agenceData.setDescription(agenceData.getDescription());
			return agenceRepo.save(agenceData);
			
		}
	}

	
}
