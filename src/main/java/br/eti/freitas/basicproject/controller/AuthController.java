package br.eti.freitas.basicproject.controller;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.eti.freitas.basicproject.model.Role;
import br.eti.freitas.basicproject.model.User;
import br.eti.freitas.basicproject.payload.LoginRequest;
import br.eti.freitas.basicproject.payload.SignUpRequest;
import br.eti.freitas.basicproject.payload.TokenResponse;
import br.eti.freitas.basicproject.repository.RoleRepository;
import br.eti.freitas.basicproject.repository.UserRepository;
import br.eti.freitas.basicproject.security.jwt.JwtTokenUtil;
import br.eti.freitas.basicproject.security.service.JwtUserDetailsService;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody @Validated LoginRequest loginDto) throws Exception {

		try {
			final Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                		loginDto.getUsername(),
	                		loginDto.getPassword()
	                )
	        );
			SecurityContextHolder.getContext().setAuthentication(authentication);
		
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final TokenResponse tokenDto = new TokenResponse();

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginDto.getUsername());

		final String token = jwtTokenService.generateToken(userDetails);

		tokenDto.setToken(token);
		tokenDto.setType("Bearer");

		return ResponseEntity.ok().body(tokenDto);
		
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpDto) {

		// check if the user exists in the database
		if (userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		// check if the email exists in the database
		if (userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}

		// create user object
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		// add default role to the user
		Optional<Role> roles = roleRepository.findByName("ROLE_USER");
		user.setRoles(Collections.singleton(roles.get()));

		userRepository.save(user);

		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

	}

}
