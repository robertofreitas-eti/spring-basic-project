package br.eti.freitas.basicproject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.eti.freitas.basicproject.model.Permission;
import br.eti.freitas.basicproject.model.Role;
import br.eti.freitas.basicproject.repository.PermissionRepository;
import br.eti.freitas.basicproject.repository.RoleRepository;
import br.eti.freitas.basicproject.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	private static final Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);
	
	private final PermissionRepository permissionRepository;
	private final RoleRepository roleRepository;
	
	public PermissionServiceImpl(PermissionRepository permissionRepository, RoleRepository roleRepository) {
		this.permissionRepository = permissionRepository;
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Optional<Permission> getPermission(String permissionName) {
		LOG.info("Fetching PERMISSION {}", permissionName);
		return permissionRepository.findByName(permissionName);
	}

	@Override
	public List<Permission> getPermissions() {
		LOG.info("Fetching all PERMISSION's");
		return permissionRepository.findAll();
	}

	@Override
	public Permission savePermission(Permission permission) {
		LOG.info("Saving new PERMISSION {} to the database", permission.getName());
		return permissionRepository.save(permission);
	}

	@Override
	public void addPermissionToRole(String roleName, String permissionName) {
		LOG.info("Adding PERMISSION {} to ROLE {}", permissionName, roleName);
		Optional<Role> role = roleRepository.findByName(roleName);
		Optional<Permission> permission = permissionRepository.findByName(permissionName);
		role.get().getPermissions().add(permission.get());
	}

}
