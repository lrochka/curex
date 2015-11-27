/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Collection;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import ua.com.curex.dao.AccountDao;
import ua.com.curex.dao.RoleDao;
import ua.com.curex.domain.Account;
import ua.com.curex.domain.Role;
import ua.com.curex.service.AccountService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
//@PreAuthorize("denyAll")
public class AccountServiceImpl implements AccountService {
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Inject private AccountDao accountDao;
	@Inject private RoleDao roleDao;
	
	@Override
	@Transactional(readOnly = false)
    //@PreAuthorize("hasRole('PERM_CREATE_ACCOUNTS')")	
	public boolean registerAccount(Account account, String[] roleForm, String password, Errors errors) {
		validateUsername(account.getUsername(), errors);
		validateEmail(account.getUsername(), account.getEmail(), errors);
		validatePhone(account.getUsername(), account.getPhone(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Collection<Role> roles = new HashSet<Role>();
			for(String roleStr : roleForm) roles.add(roleDao.findByCode(roleStr));
			//roles.add(roleDao.findByCode("ROLE_USER"));
			account.setRoles(roles);
			accountDao.create(account, password);
		}
		
		return valid;
	}
	
	private void validateUsername(String username, Errors errors) {
		if (accountDao.findByUsername(username) != null) {
			log.debug("Validation failed: duplicate username");
			errors.rejectValue("username", "error.duplicate", new String[] { username }, "Этот логин уже используется!");
		}
	}

	private void validateEmail(String username, String email, Errors errors) {
		List<Account> accountList = this.getAccountList();
		boolean check = false;
		for(Account a:accountList) {
			if ((!a.getUsername().equals(username))&&(a.getEmail().equals(email))) check=true;
			} 
		if (check) {
			log.debug("Validation failed: duplicate email");
			errors.rejectValue("email", "error.duplicate", new String[] { email }, "Этот email уже используется!");
		}
	}

	private void validatePhone(String username, String phone, Errors errors) {
		List<Account> accountList = this.getAccountList();
		boolean check = false;
		for(Account a:accountList) {
			if ((!a.getUsername().equals(username))&&(a.getPhone().equals(phone))) check=true;
			} 
		if (check) {
			log.debug("Validation failed: duplicate phone");
			errors.rejectValue("phone", "error.duplicate", new String[] { phone }, "Этот номер телефона уже используется!");
		}
	}

	@Override
	// @PreAuthorize("hasRole('PERM_READ_ACCOUNTS')")
	// @PostFilter("hasPermission(filterObject, read)")
    public List<Account> getAccountList(){
		return accountDao.getAccountList();
	}
	
	@Override
	// @PreAuthorize("hasRole('PERM_READ_ACCOUNTS')")
	// @PostFilter("hasPermission(filterObject, read)")
    public List<Account> getAccountListByCompany(Long id){
		return accountDao.getAccountListByCompany(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	//@PreAuthorize("(hasPermission(#account, write) or hasPermission(#account, admin)")
	public void setAccountInfo(Account account, String[] roleForm, String password, Errors errors){
		validateEmail(account.getUsername(), account.getEmail(), errors);
		validatePhone(account.getUsername(), account.getPhone(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			Collection<Role> roles = new HashSet<Role>();
			for(String roleStr : roleForm) roles.add(roleDao.findByCode(roleStr));
			//roles.add(roleDao.findByCode("ROLE_USER"));
			account.setRoles(roles);
			accountDao.update(account,password);
		}
		//updateAcl(account);
	}

	@Override
	//@PreAuthorize("hasRole('PERM_READ_ACCOUNTS')")
	//@PostAuthorize("hasPermission(returnObject, read)")
	public Account getAccountByUsername(String username) {
		Account account = accountDao.findByUsername(username);
		if (account != null) { Hibernate.initialize(account.getRoles()); }
		return account;
	}
}
