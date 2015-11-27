/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.util.List;

import org.springframework.validation.Errors;

import ua.com.curex.domain.Account;

/**
 * @author Irochka
 */
public interface AccountService {

	/**
	 * Registers the given account, but only if the account and password are valid. This method can perform checks
	 * against the database (e.g., duplicate username or e-mail) to assess validity.
	 * 
	 * @param account
	 *            account data
	 * @param roles
	 *            user roles
	 * @param password
	 *            user password
	 * @param errors
	 *            object for collecting and logging errors
	 * @return flag indicating whether the account was registered
	 */
	boolean registerAccount(Account account, String[] roles, String password, Errors errors);
	
	public List<Account> getAccountList();
	
	public List<Account> getAccountListByCompany(Long id);
	
	public void setAccountInfo(Account account, String[] roles, String password, Errors errors);
	
	/**
	 * Returns the requested account with roles hydrated, or <code>null</code> if no such account exists.
	 * 
	 * @return the requested account, or <code>null</code> if it doesn't exist
	 */
	Account getAccountByUsername(String username);
}
