package br.eti.freitas.basicproject.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.eti.freitas.basicproject.model.Role;
import br.eti.freitas.basicproject.service.RoleService;

@Controller
@RequestMapping("/api/v1")
public class RoleController {

	private final RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping("/role")
	public ResponseEntity<Role> getRole(@RequestParam String roleName) {
		return ResponseEntity.ok().body(roleService.getRole(roleName).get());
	}

	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getRoles() {
		return ResponseEntity.ok().body(roleService.getRoles());
	}

	@PostMapping("/role")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Role> saveRole(@RequestBody Role role) {
		URI location = URI.create(
				ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/role").toUriString().toString());
		return ResponseEntity.created(location).body(roleService.saveRole(role));
	}
	
	@PostMapping("/role/user")
	public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
		roleService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
}

class RoleToUserForm {

	private String username;
	private String roleName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
