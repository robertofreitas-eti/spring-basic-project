package br.eti.freitas.basicproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.basicproject.model.User;
import br.eti.freitas.basicproject.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		URI location = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user").toUriString().toString());
		return ResponseEntity.created(location).body(userService.saveUser(user));
	}
	
}
