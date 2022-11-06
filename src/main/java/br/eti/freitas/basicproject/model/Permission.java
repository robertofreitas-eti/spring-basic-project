package br.eti.freitas.basicproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PERMISSION")
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionId;
	
	@Column(length = 40, nullable = false, unique = true)
	private String name;
	
	private boolean enabled;
	
	public Permission() {
	}
	
	public Permission(Long permissionId, String name, Boolean enabled) {
		this.permissionId = permissionId;
		this.name = name;
		this.enabled = enabled;
	}

	public Long getPermissionId() {
		return permissionId;
	}
	
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
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

	
}
