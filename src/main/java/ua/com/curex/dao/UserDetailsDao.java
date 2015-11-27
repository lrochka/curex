/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

/**
 * Supplements {@link ua.com.curex.service.AccountService} by providing a way to look up a user's password.
 * We need this to implement the {@link org.springframework.security.core.userdetails.UserDetailsService} interface.
 * 
 * @author Irochka
 */
public interface UserDetailsDao {

	/**
	 * <p>
	 * Return the password associated with a given username.
	 * </p>
	 * 
	 * @param username
	 *            username for which the password is desired
	 * @return password
	 */
	String findPasswordByUsername(String username);
}
