package br.eti.freitas.basicproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.eti.freitas.basicproject.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Optional<Role> findByName(String name);
	
}
