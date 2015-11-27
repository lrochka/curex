/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.util.List;

import ua.com.curex.domain.Account;
import ua.com.curex.dao.Dao;

/**
 * @author Irochka
 */
public interface AccountDao{
	
	void create(Account account, String password);
	
	void update(Account account, String password);
	
	Account findByUsername(String username);
	
	public List<Account> getAccountList();
	
	public List<Account> getAccountListByCompany(Long id);
}
