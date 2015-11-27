/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Irochka
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "role")
@NamedQuery(name = "role.byCode", query = "from Role where code= :code")
public class Role implements GrantedAuthority{
	private Long id;
	private String code;
	private String name;
	private Set<Permission> permissions = new HashSet<Permission>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	@Column(name = "scode")
	public String getCode() { return code; }

	public void setCode(String code) { this.code = code; }
	
	@Column(name = "sname")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "role_permission",
		joinColumns = { @JoinColumn(name = "nrole") },
		inverseJoinColumns = { @JoinColumn(name = "npermission") })
	public Set<Permission> getPermissions() { return permissions; }
	
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	@Transient
	public String getAuthority() {
		return code;
	}
	
	@Override
	public boolean equals(Object o) {
		GrantedAuthority ga = (GrantedAuthority) o;
		return (getAuthority().equals(ga.getAuthority()));
	}
	
	@Override
	public int hashCode() {
		return getAuthority().hashCode();
	}
}
