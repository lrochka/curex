/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import org.hibernate.validator.constraints.Email;	

import org.springframework.security.core.GrantedAuthority;
/**
 * @author Irochka
 */
@Entity
@Table(name = "userlist")
@NamedQuery(name = "userlist.byLogin",
	query = "from Account a where a.username = :username")
@SuppressWarnings("serial")
public class Account {
	public static final Account ACCOUNT = new Account("anonymous");
	
	private Long id;
	private Company company;
	protected String username;
	private String firstName, lastName, email, phone;
	private boolean enabled = true;
	private Date dateCreated;
	private Collection<Role> roles = new HashSet<Role>();

	public Account() { }
	
	public Account(String username) { this.username = username; }
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	// @NotNull
	@ManyToOne
	@JoinColumn(name="ncompany", referencedColumnName = "id")
	public Company getCompany() { return company; }
	
	public void setCompany(Company company) { this.company = company; }
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "slogin")
	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "sfirst_name")
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "slast_name")
	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@Transient
	public String getFullName() { return this.firstName + " " + this.lastName + 
			" {" + this.getCompany().getCode().toUpperCase() +": "+ this.getCompany().getName() +"}";} 
	
	@NotNull
	@Size(min = 6, max = 50, message = "Не может быть меньше 6 символов")
	@Email
	@Column(name = "semail")
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	
	@NotNull
	@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}", message="(xxx)xxx-xx-xx")
	//@Digits(integer = 10, fraction = 0, message = "Телефонный номер состоит из 10 цифр!")
	@Column(name = "sphone")
	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }
	
	@Column(name = "benabled")
	public boolean isEnabled() { return enabled; }

	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	@ManyToMany
	@JoinTable(
		name = "userrole",
		joinColumns = { @JoinColumn(name = "nuser", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "nrole", referencedColumnName = "id") })
	public Collection<Role> getRoles() { return roles; }
	
	public void setRoles(Collection<Role> roles) { this.roles = roles; }
	
	@Column(name = "ddate_created")
	public Date getDateCreated() { return dateCreated; }
	
	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
	
	@Transient
	public Set<Permission> getPermissions() {
		Set<Permission> perms = new HashSet<Permission>();
		for (Role role : roles) { perms.addAll(role.getPermissions()); }
		return perms;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(getRoles());
		authorities.addAll(getPermissions());
		return authorities;
	}
	

	public boolean hasRole(String role){
		for (Role r : getRoles()) {
			if (r.getCode().equals(role)) return true;
		}
		return false;
	}
	/**
	 * <p>
	 * Returns the username.
	 * </p>
	 */
	public String toString() { return username; }
}
