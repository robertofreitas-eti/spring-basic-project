package br.eti.freitas.basicproject.service;

import java.util.List;
import java.util.Optional;

import br.eti.freitas.basicproject.model.User;

public interface UserService {

	Optional<User> getUser(String username);
	
	List<User> getUsers();

	User saveUser(User user);
	
}
