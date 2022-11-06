package br.eti.freitas.basicproject.service;

import java.util.List;
import java.util.Optional;

import br.eti.freitas.basicproject.model.Role;

public interface RoleService {
	
	Optional<Role> getRole(String roleName); 
	
	List<Role> getRoles();

	Role saveRole(Role role);
	
	void addRoleToUser(String username, String roleName);
}
