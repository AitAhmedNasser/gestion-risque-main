//package Auth.Filter;
package it.gestionRisque.app.auth.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import it.gestionRisque.app.auth.service.ServiceImp.UserDetailsImp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(AuthTockenFilter.class);
	

	public final static String jwtSecret = "secret12309";
	public static final  String AUTH_HEADER = "Authorization";
	public static final  String PREFIX = "Bearer ";
	public static final  long EXCPIRE_ACCESS_TOKEN =1*60*1000;

	// private final int jwtExpirationMs = new Date(System.currentTimeMillis() +
	// 25*60* 1000);

	public static String generateJwtAccessToken(Authentication authentication) {

		UserDetailsImp userPrincipal = (UserDetailsImp) authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secret12309");
		return JWT.create()
				.withSubject(userPrincipal.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+  60* 60 * 1000))
				.withClaim("roles", userPrincipal.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
				.sign(algorithm);
			

	}
	

	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey("secret12309").parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}

}
