/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.AccountDao;
import ua.com.curex.domain.Account;
import ua.com.curex.domain.Client;
import ua.com.curex.domain.UserDetailsAdapter;

/**
 * @author Irochka
 */
@Repository
public class HbnAccountDao extends AbstractHbnDao<Account> implements AccountDao {
	private static final Logger log = LoggerFactory.getLogger(HbnAccountDao.class);
	
	private static final String UPDATE_PASSWORD_SQL =
		"update userlist set spassword = ? where slogin = ?";
	
	private static final String SELECT_ACCOUNTS_BY_COMPANY_SQL =
    		"select * from userlist where ncompany = ?";
	
	@Inject private JdbcTemplate jdbcTemplate;
	@Inject private PasswordEncoder passwordEncoder;
	@Inject private SaltSource saltSource;
	
	public void create(Account account, String password) {
		log.debug("Creating account: {}", account);
		create(account);
		
		log.debug("Updating password");
		Object salt = saltSource.getSalt(new UserDetailsAdapter(account));
		String encPassword = passwordEncoder.encodePassword(password, salt);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
	}
	
	public void update(Account account, String password){
		log.debug("Updating account: {}", account);
		merge(account);
		
		log.debug("Updating password");
		Object salt = saltSource.getSalt(new UserDetailsAdapter(account));
		String encPassword = passwordEncoder.encodePassword(password, salt);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getUsername());
	}

	public Account findByUsername(String username) {
		return (Account) getSession()
				.getNamedQuery("userlist.byLogin")
				.setParameter("username", username)
				.uniqueResult();
	}
	
	public List<Account> getAccountList(){
		return this.getAll();
	}
	
	public List<Account> getAccountListByCompany(Long id){
		List<Account> accountList = new ArrayList<Account>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_ACCOUNTS_BY_COMPANY_SQL, id);
		
		for(Map<String, Object> row : rows) {
			
			Account account = findByUsername((String)row.get("slogin"));
			
			accountList.add(account);
			}
		
		return accountList;
	}

	@Override
	public Page<Account> findAll(int page,int size) {
		return findAll(page,size);
	}
}
