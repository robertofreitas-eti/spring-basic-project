package br.eti.freitas.basicproject.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.eti.freitas.basicproject.model.User;
import br.eti.freitas.basicproject.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail);

	}

}
