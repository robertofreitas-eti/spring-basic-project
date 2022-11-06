package br.eti.freitas.basicproject.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.eti.freitas.basicproject.model.Role;
import br.eti.freitas.basicproject.model.User;
import br.eti.freitas.basicproject.repository.RoleRepository;
import br.eti.freitas.basicproject.repository.UserRepository;
import br.eti.freitas.basicproject.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private static Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	public RoleServiceImpl (RoleRepository roleRepository, UserRepository userRepository) {	
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Optional<Role> getRole(String roleName) {
		LOG.info("Fetching ROLE {}", roleName);
		return roleRepository.findByName(roleName);
	}
	
	@Override
	public List<Role> getRoles() {
		LOG.info("Fetching all ROLE's");
		return roleRepository.findAll();
	}

	@Override
	public Role saveRole(Role role) {
		LOG.info("Saving new ROLE {} to the database", role.getName());
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		LOG.info("Adding ROLE {} to USER {}", roleName, username);
		Optional<User> user = userRepository.findByUsername(username);
		Optional<Role> role = roleRepository.findByName(roleName);
		user.get().getRoles().add(role.get());
	}

}
