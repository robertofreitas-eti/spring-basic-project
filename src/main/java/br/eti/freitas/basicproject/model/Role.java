package br.eti.freitas.basicproject.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "ROLE")
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	@Column(length = 40, nullable = false, unique = true)
	private String name;

	private boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PERMISSION", 
				 joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId")}, 
				   inverseJoinColumns = {@JoinColumn(name = "PermissionId", referencedColumnName = "PermissionId")})
	private Collection<Permission> permissions = new ArrayList<>();

	public Role() {
	}

	public Role(Long roleId, String name, boolean enabled) {
		this.roleId = roleId;
		this.name = name;
		this.enabled = enabled;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}
