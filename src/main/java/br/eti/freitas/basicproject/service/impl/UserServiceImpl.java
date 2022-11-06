package br.eti.freitas.basicproject.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.eti.freitas.basicproject.model.User;
import br.eti.freitas.basicproject.repository.UserRepository;
import br.eti.freitas.basicproject.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder=  passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		if (!user.isPresent()) {
			LOG.error("User not found");
			throw new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
		}
		LOG.error("User found {}", usernameOrEmail);

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.get().getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		
		return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
				authorities);
	}
	
	@Override
	public User saveUser(User user) {
		LOG.info("Saving new USER {} to the database", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public Optional<User> getUser(String username) {
		LOG.info("Fetching USER {} ", username);
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		LOG.info("Fetching all USER's");
		return userRepository.findAll();
	}

}
