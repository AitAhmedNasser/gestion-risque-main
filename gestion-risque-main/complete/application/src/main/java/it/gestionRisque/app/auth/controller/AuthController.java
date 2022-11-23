//package Auth.Controller;
package it.gestionRisque.app.auth.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gestionRisque.app.auth.filter.JwtUtils;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.repository.UserRepository;
import it.gestionRisque.app.auth.service.ServiceImp.UserDetailsImp;
import it.gestionRisque.app.auth.entities.User;
import it.gestionRisque.app.auth.request.AuthRequest;
import it.gestionRisque.app.auth.request.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Auth")
@CrossOrigin("*")
@Slf4j
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	UserDetailsImp userDetailsImp;
	
	
	
	@PostMapping("/signin")
	
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) throws Exception {

		try {
			User usr = userRepository.getUserByUsername(authRequest.getUsername()); 
			if(usr.getUsername() == null || usr.getPassword() == null) {
			
				return new ResponseEntity(" not found ", HttpStatus.NOT_FOUND);
			}else {
				
		
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
				log.info(authRequest.getUsername());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwtAccessTocken = jwtUtils.generateJwtAccessToken(authentication);
				UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();		
				System.out.println(userDetails.getAuthorities());

				List<String> permissions = userDetails.getAuthorities().stream()
						.map(item -> item.getAuthority())
						.collect(Collectors.toList());
				
				
				
				return  ResponseEntity.ok(new JwtResponse(jwtAccessTocken,userDetails.getRoles(),userDetails.getNom(),userDetails.getPrenom(),userDetails.getEmail(),permissions));
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity("Username ou mot de passe incorrect", HttpStatus.BAD_REQUEST);
		}
	
			}
		
}
