//package Auth.Filter;
package it.gestionRisque.app.auth.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.gestionRisque.app.auth.repository.UserRepository;
import it.gestionRisque.app.auth.service.ServiceImp.UserDetailsServiceImp;
import it.gestionRisque.app.auth.entities.Privilege;
import it.gestionRisque.app.auth.entities.Role;
import it.gestionRisque.app.auth.entities.User;

@Component
public class AuthTockenFilter extends OncePerRequestFilter{
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImp userDetailsService;

	@Autowired
	private UserRepository userRepo;
	

	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if( request.getServletPath().equals("/Auth/signin") ) {
			filterChain.doFilter(request, response);
		}else {
		String authorizationTocken = request.getHeader("Authorization");
		if(authorizationTocken != null && authorizationTocken.startsWith("Bearer ")) {
			try {
				String jwt = authorizationTocken.substring(7);
				Algorithm algo = Algorithm.HMAC256("secret12309");
				JWTVerifier jwtverify =JWT.require(algo).build();
				DecodedJWT decodedJwt =jwtverify.verify(jwt);
				String username = decodedJwt.getSubject();
				User user = userRepo.getUserByUsername(username);
				String[] roles = decodedJwt.getClaim("roles").asArray(String.class); 
				Collection< GrantedAuthority> authorities =new ArrayList<>();
				for(String r:roles) {
					authorities.add(new SimpleGrantedAuthority(r));
				}
				
				UsernamePasswordAuthenticationToken authenticationTocken = new UsernamePasswordAuthenticationToken(username,null,authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationTocken);
				filterChain.doFilter(request, response);
			
			} catch (Exception e) {
				response.setHeader("error-message", e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
				
		
			}else {
					filterChain.doFilter(request, response);
		}
	}
	
	}
}

