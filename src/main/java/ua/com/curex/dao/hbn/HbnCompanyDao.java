/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.math.BigInteger;
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
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.CompanyDao;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;

/**
 * @author Irochka
 */
@Repository
public class HbnCompanyDao extends AbstractHbnDao<Company> implements CompanyDao {
	private static final Logger log = LoggerFactory.getLogger(HbnCompanyDao.class);
	
	private static final String SELECT_CURRENCIES_BY_COMPANY_SQL =
    		"select * from companycurrency where ncompany = ?";
	
	@Inject private JdbcTemplate jdbcTemplate;
	
	public void createCompany(Company c) {
		log.debug("Creating company: {}", c);
		create(c);
	}
	
	public void updateCompany(Company c){
		log.debug("Updating company: {}", c);
		merge(c);
	}
	
	public List<Company> getCompanyList(){
		return this.getAll();
	}
	
	public List<Currency> getCompanyCurrencyList(Long id){

		List<Currency> currencyList = new ArrayList<Currency>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CURRENCIES_BY_COMPANY_SQL, id);
		
		for(Map<String, Object> row : rows) {
			
			Currency currency = currencyFindById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			
			currencyList.add(currency);
			}
		
		return currencyList;
	}

	private Currency currencyFindById(Long id){
		return (Currency) getSession()
				.getNamedQuery("currencies.byId")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public Company findById(Long id) {
		return (Company) getSession()
				.getNamedQuery("companies.byId")
				.setParameter("id", id)
				.uniqueResult();
	}

	public Company findByCode(String code) {
		return (Company) getSession()
				.getNamedQuery("companies.byCode")
				.setParameter("code", code)
				.uniqueResult();
	}

	@Override
	public Page<Company> findAll(int page,int size) {
		return findAll(page,size);
	}
	
}