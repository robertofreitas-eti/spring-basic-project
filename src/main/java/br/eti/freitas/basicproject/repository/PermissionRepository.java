package br.eti.freitas.basicproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.eti.freitas.basicproject.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Optional<Permission> findByName(String name);
	
}
