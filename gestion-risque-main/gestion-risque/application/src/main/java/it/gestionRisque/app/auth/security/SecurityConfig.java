//package Auth.Security;
package it.gestionRisque.app.auth.security;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import  it.gestionRisque.app.auth.filter.AuthTockenFilter;
//import Auth.Filter.JwtAuthentificationFilter;
import it.gestionRisque.app.auth.filter.JwtEntryPoint;
import it.gestionRisque.app.auth.service.ServiceImp.UserDetailsServiceImp;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true)

public class SecurityConfig  {

private final PasswordEncoder passwordEncoder;


private final UserDetailsService userDetailsService;

@Autowired
private JwtEntryPoint unauthorizedHandler;

public SecurityConfig(PasswordEncoder passwordEncoder,UserDetailsService userDetailsService) {
	super();
	this.passwordEncoder = passwordEncoder;
	this.userDetailsService = userDetailsService;

	}
	@Bean
	public AuthTockenFilter authenticationJwtTokenFilter() {
		return new AuthTockenFilter();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailService) 
	  throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(userDetailsService)
	      .passwordEncoder(bCryptPasswordEncoder)
	      .and()
	      .build();
	}


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and()
	    .csrf()
	    .disable()
	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			 .authorizeRequests(authorizeRequests ->
		        authorizeRequests
			            .antMatchers("/portfeuilleIndirect/**").hasAnyAuthority("ConsulterUser")
			    )

				.authorizeRequests().antMatchers("/Auth/**","api/Ressources/","api/privileges","/creditParticulier/**","/seed").permitAll()
				.anyRequest().permitAll();

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	
}
