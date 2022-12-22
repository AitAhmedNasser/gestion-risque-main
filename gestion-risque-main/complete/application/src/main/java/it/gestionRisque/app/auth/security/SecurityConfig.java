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
import org.springframework.security.crypto.password.PasswordEncoder;
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

public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		 .authorizeRequests(authorizeRequests ->
	        authorizeRequests
	            .antMatchers("/api/user").hasAnyAuthority("ConsulterUser")
	            .antMatchers("/api/roles").hasAnyAuthority("ConsulterRole")
	            .antMatchers("/api/ressources").hasAnyAuthority("ConsulterRessources")
	            .antMatchers("/creditParticulier/**").hasAnyAuthority("ConsulterUser")
	    )

		.authorizeRequests().antMatchers("/Auth/signin","api/Ressources/","api/privileges","/creditParticulier/**").permitAll()
		.anyRequest().permitAll();

	http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	


	
	
}
