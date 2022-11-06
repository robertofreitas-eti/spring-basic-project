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
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@Column(length = 60, nullable = false)
	private String name;

	@Column(length = 100, nullable = false)
	private String email;

	@Column(length = 40, nullable = false, unique = true)
	private String username;

	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", 
				joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")}, 
				  inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId")})
	private Collection<Role> roles = new ArrayList<>();

	public User() {
	}

	public User(Long userId, String name, String email, String username, String password, Collection<Role> roles) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
