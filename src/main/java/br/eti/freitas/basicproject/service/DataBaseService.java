package br.eti.freitas.basicproject.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.eti.freitas.basicproject.model.Permission;
import br.eti.freitas.basicproject.model.Role;
import br.eti.freitas.basicproject.model.User;

@Service
public class DataBaseService {

	private UserService userService;

	private RoleService roleService;

	private PermissionService permissionService;
	
	public DataBaseService(UserService userService, RoleService roleService, PermissionService permissionService) {
		this.userService = userService;
		this.roleService = roleService;
		this.permissionService = permissionService;
	}

	public void iniciarBancoTeste() {

		// Add roles
		roleService.saveRole(new Role(null, "ROLE_USER", true));
		roleService.saveRole(new Role(null, "ROLE_MANAGER", true));
		roleService.saveRole(new Role(null, "ROLE_ADMIN", true));
		roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN", true));
		
		// Add Permissions
		permissionService.savePermission(new Permission(null, "PERM_ADD_USER", true));
		permissionService.savePermission(new Permission(null, "PERM_UPDATE_USER", true));
		permissionService.savePermission(new Permission(null, "PERM_DELETE_USER", true));

		
		// Add Permission to Role
		permissionService.addPermissionToRole("ROLE_USER", "PERM_ADD_USER");
		permissionService.addPermissionToRole("ROLE_USER", "PERM_UPDATE_USER");
		permissionService.addPermissionToRole("ROLE_USER", "PERM_DELETE_USER");
		
		// Add users
		userService.saveUser(new User(null, "Bill Gates", "bill.gates@microsoft.com", "bill.gates", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Henry Ford", "henry.ford@ford.com", "henry.ford", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Mark Zuckerberg", "mark.zuckerberg@facebook.com", "mark.zuckerberg", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Sergey Brin", "sergey.brin@odiseia.com", "sergey.brin", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Thomas Edson", "thomas.edson@energia.com", "thomas.edson", "1234", new ArrayList<>()));
		
		// Add role to user
		roleService.addRoleToUser("bill.gates", "ROLE_USER");
		roleService.addRoleToUser("bill.gates", "ROLE_MANAGER");
		roleService.addRoleToUser("henry.ford", "ROLE_MANAGER");
		roleService.addRoleToUser("mark.zuckerberg", "ROLE_SUPER_ADMIN");
		roleService.addRoleToUser("sergey.brin", "ROLE_ADMIN");
		roleService.addRoleToUser("thomas.edson", "ROLE_SUPER_ADMIN");
		roleService.addRoleToUser("thomas.edson", "ROLE_ADMIN");
		roleService.addRoleToUser("thomas.edson", "ROLE_USER");
	}

}
