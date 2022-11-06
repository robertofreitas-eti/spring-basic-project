package br.eti.freitas.basicproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.eti.freitas.basicproject.security.jwt.JwtAuthenticationEntryPoint;
import br.eti.freitas.basicproject.security.jwt.JwtRequestFilter;
import br.eti.freitas.basicproject.security.service.JwtUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Bean
	public JwtRequestFilter jwtTokenFilter() {
		return new JwtRequestFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(jwtUserDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		/*
		 *  Enable CORS and disable CSRF
		 */
		http.cors().and().csrf().disable();
		
		/*
		 * We use the stateless session and do not store the user’s state in the session.
		 */
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and();
				
		/*
		 * Set permissions for all endpoints
		 */
		http.authorizeRequests()
				/*
				 * this specific endpoint is not required to authenticate
				 */
				.antMatchers("/api/v1/auth/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/healt/**").permitAll()
				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-ui/**").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/webjars/**").permitAll()
				/*
				 * Anothers endpint are private 
				 */
				.anyRequest().authenticated().and();

	    http.authenticationProvider(authenticationProvider());
	    
		http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
	    
		/*
		 * Check another sessions and identify if the JWT is present on header
		 */
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
