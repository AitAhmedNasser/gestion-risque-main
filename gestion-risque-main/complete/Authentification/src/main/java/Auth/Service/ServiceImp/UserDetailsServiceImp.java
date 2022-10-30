package Auth.Service.ServiceImp;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import Auth.Repository.UserRepository;
import Auth.entities.User;

@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

	private final UserRepository userRepository;
	
	public UserDetailsServiceImp(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		return UserDetailsImp.build(user);
	}

	
}
