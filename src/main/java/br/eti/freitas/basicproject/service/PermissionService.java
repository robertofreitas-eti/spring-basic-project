package br.eti.freitas.basicproject.service;

import java.util.List;
import java.util.Optional;

import br.eti.freitas.basicproject.model.Permission;

public interface PermissionService {

	Optional<Permission> getPermission(String permissionName);
	
	List<Permission> getPermissions();
	
	Permission savePermission(Permission permission);
	
	void addPermissionToRole(String roleName, String permissionName);
	
}
