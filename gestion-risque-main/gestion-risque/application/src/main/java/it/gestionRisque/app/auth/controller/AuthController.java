//package Auth.Controller;
package it.gestionRisque.app.auth.controller;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

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

import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

import it.gestionRisque.app.auth.repository.RestPasswordTokenRepository;
import it.gestionRisque.app.auth.repository.RoleRepository;
import it.gestionRisque.app.auth.repository.UserRepository;
import it.gestionRisque.app.auth.service.AccountService;
import it.gestionRisque.app.auth.service.MailService;
import it.gestionRisque.app.auth.service.ResetPasswordToken;
import it.gestionRisque.app.auth.service.ServiceImp.UserDetailsImp;
import it.gestionRisque.app.auth.entities.Mail;
import it.gestionRisque.app.auth.entities.PasswordForgot;
import it.gestionRisque.app.auth.entities.PasswordReset;
import it.gestionRisque.app.auth.entities.PasswordResetToken;
import it.gestionRisque.app.auth.entities.User;
import it.gestionRisque.app.auth.request.AuthRequest;
import it.gestionRisque.app.auth.request.GenericResponsePasswordReset;

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

	@Autowired
	ResetPasswordToken restTokenSer;

	@Autowired
	MailService mailService;

	@Autowired
	RestPasswordTokenRepository ResetPasswordRepository;

	@Autowired
	AccountService accountService;

	@PostMapping("/signin")

	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody AuthRequest authRequest) throws Exception {

		try {
			User usr = userRepository.getUserByUsername(authRequest.getUsername());
			if (usr.getUsername() == null || usr.getPassword() == null) {

				return new ResponseEntity(" not found ", HttpStatus.NOT_FOUND);
			} else {

				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
				//log.info(authRequest.getUsername());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwtAccessTocken = jwtUtils.generateJwtAccessToken(authentication);
				UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
				//System.out.println(userDetails.getAuthorities());

				List<String> permissions = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				return ResponseEntity.ok(new JwtResponse(jwtAccessTocken, userDetails.getRoles(), userDetails.getNom(),
						userDetails.getPrenom(), userDetails.getEmail(), permissions));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity("Username ou mot de passe incorrect", HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/ForgotPassword")
	public ResponseEntity<GenericResponsePasswordReset> ForgotPassword(HttpServletRequest request,
			@RequestBody PasswordForgot passwordForgot, Model modelFarmework) throws AccountNotFoundException {

		User user = userRepository.getUserByEmail(passwordForgot.getEmail());

		if (user == null) {
			throw new AccountNotFoundException("User not found");
		}

		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken();
		passwordResetToken.setToken(token);
		passwordResetToken.setUser(user);
		passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
		restTokenSer.savePasswordResetToken(passwordResetToken);

		Mail mail = new Mail();
		mail.setFrom("Intervalle-Technologies.com");
		mail.setTo(user.getEmail());
		mail.setSubject("Reset your password");

		Map<String, Object> model = new HashMap<>();
		model.put("token", token);
		model.put("Username", user.getUsername());
		model.put("Email", user.getEmail());
		model.put("expiryDate", passwordResetToken.getExpiryDate());

		String resetPasswordLink = "http://localhost:4200/ResetPassword/" + passwordResetToken.getToken();

		model.put("Link", resetPasswordLink);

		mail.setModel(model);
		mailService.send(mail);

		return ResponseEntity.ok(new GenericResponsePasswordReset("token est généré ", ""));

	}
//

	@PostMapping("/ResetPassword/{token}")
	public ResponseEntity<PasswordResetToken> ResetPassword(HttpServletRequest request, @PathVariable String token,
			@RequestBody PasswordReset passwordReset) throws AccountNotFoundException {

		PasswordResetToken resetToken = ResetPasswordRepository.findByToken(token);

		if (resetToken == null) {
			return new ResponseEntity("Le token n'est pas générer !!!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (restTokenSer.isTokenExpired(resetToken)) {
			return new ResponseEntity("Le token est expiré", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (restTokenSer.isTokenUsed(resetToken) == true) {
			return new ResponseEntity("Le token est utilisé déja ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		User user = resetToken.getUser();

		if (user == null) {
			return new ResponseEntity("User not Found ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (passwordReset.getPassword() == "" || passwordReset.getConfirmPassword() == ""
				|| passwordReset.getPassword() == null || passwordReset.getConfirmPassword() == null) {
			return new ResponseEntity("Password and confirm Password required !  ", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!passwordReset.getPassword().toString().equals(passwordReset.getConfirmPassword().toString())) {
			return new ResponseEntity("Password and confirm Password must be identical !",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String updatedPassword = passwordReset.getPassword();
		accountService.UpdatePasswordUser(updatedPassword, user.getId());
		resetToken.setUsed(true);
		ResetPasswordRepository.save(resetToken);

		return new ResponseEntity(resetToken, HttpStatus.CREATED);
	}

	@GetMapping("/CheckResetPassword/{token}")
	public ResponseEntity<PasswordResetToken> CheckTokenPassword(@PathVariable String token) {
		PasswordResetToken passwordReset = ResetPasswordRepository.checkUserToken(token);

		if (passwordReset == null) {
			return new ResponseEntity(passwordReset, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(passwordReset, HttpStatus.ACCEPTED);
	}

	// End of class
}
