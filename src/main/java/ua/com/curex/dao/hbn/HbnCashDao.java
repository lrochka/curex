/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.CurrencyDao;
import ua.com.curex.dao.CashDao;
import ua.com.curex.dao.CompanyDao;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Cash;

/**
 * @author Irochka
 */
@Repository
public class HbnCashDao extends AbstractHbnDao<Cash> implements CashDao{
	private static final Logger log = LoggerFactory.getLogger(HbnCashDao.class);

	private static final String SELECT_CASHBOOK_BY_TYPE_SQL =
    		"select * from cashbook where ntype = ?";
	
	private static final String SELECT_CASHBOOK_BY_COMPANY_SQL =
    		"select * from cashbook where ncompany = ?";
	
	private static final String SELECT_CASHBOOK_BY_COMPANY_TYPE_SQL =
    		"select * from cashbook where ncompany = ? and ntype = ?";
	
	private static final String SELECT_CASHBOOK_BY_COMPANY_DATE_SQL =
    		"select * from cashbook where ncompany = ? and ncurrency = ? and ddate = ?";
	
	private static final String SELECT_CASHBOOK_BY_COMPANY_DATE_RANGE_SQL =
    		"select * from cashbook where ncompany = ? and ncurrency = ? and ddate between ? and ?";
	
	public void create(Cash Cash) {
		log.debug("Creating Cash: {}", Cash);
		create(Cash);
	}
	
	public Cash findByID(Long id){
		return (Cash) getSession()
				.getNamedQuery("cashbook.byId")
				.setParameter("id", id)
				.uniqueResult();
	}
	public Cash findByCompanyCurrencyDateType(Company company, Currency currency, Date date, int type){
		return (Cash) getSession()
				.getNamedQuery("cashbook.byCompanyCurrencyDateType")
				.setParameter("company", company)
				.setParameter("currency", currency)
				.setParameter("date", date)
				.setParameter("type", type)
				.uniqueResult();
	}

	@Inject private JdbcTemplate jdbcTemplate;
	@Inject private CurrencyDao currencyDao;
	@Inject private CompanyDao companyDao;
	
	public void updateCash(Cash Cash){
		log.debug("Updating Cash: {}", Cash);
		merge(Cash);
	}
	
	public List<Cash> getCashList(){
		return this.getAll();
	}

	public List<Cash> getCashList(int type){

		List<Cash> cashList = new ArrayList<Cash>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CASHBOOK_BY_TYPE_SQL, type);
		
		for(Map<String, Object> row : rows) {
			
			Cash cash = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			cash.setCodecurrency(cash.getCurrency().getAlpha());
			cash.setNamecompany(cash.getCompany().getCode()+": "+cash.getCompany().getName());
			cashList.add(cash);
			}
		
		return cashList;
	}
	
	public List<Cash> getCashList(Company company){

		List<Cash> cashList = new ArrayList<Cash>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CASHBOOK_BY_COMPANY_SQL, company.getId());
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Cash cash = findByCompanyCurrencyDateType(company,currency,Date.valueOf(((Date)row.get("ddate")).toString()),(int)row.get("ntype"));
			
			cashList.add(cash);
			}
		
		return cashList;
	}
	
	public List<Cash> getCashList(Company company, int type){

		List<Cash> cashList = new ArrayList<Cash>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CASHBOOK_BY_COMPANY_TYPE_SQL, company.getId(), type);
		
		for(Map<String, Object> row : rows) {

			Cash cash = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			cash.setCodecurrency(cash.getCurrency().getAlpha());
			cash.setNamecompany(cash.getCompany().getCode()+": "+cash.getCompany().getName());
			cashList.add(cash);
			
			}
		
		return cashList;
	}
	
	public List<Cash> getCashList(Company company, Date date){

		List<Cash> cashList = new ArrayList<Cash>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CASHBOOK_BY_COMPANY_DATE_SQL, company.getId(), date);
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Cash cash = findByCompanyCurrencyDateType(company,currency,date,(Integer)row.get("ntype"));
			
			cashList.add(cash);
			}
		
		return cashList;
	}
	
	public List<Cash> getCashList(Company company, Date bdate, Date edate){

		List<Cash> cashList = new ArrayList<Cash>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CASHBOOK_BY_COMPANY_DATE_RANGE_SQL, company.getId(), bdate,edate);
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Cash cash = findByCompanyCurrencyDateType(company,currency,Date.valueOf(((Date)row.get("ddate")).toString()),(Integer)row.get("ntype"));
			
			cashList.add(cash);
			}
		
		return cashList;
	}

	@Override
	public Page<Cash> findAll(int page,int size) {
		return findAll(page,size);
	}

	@Override
	public Page<Cash> findAllCashByType(int type, int page, int size) {
		int toindex = Math.min(page*size, this.getCashList(type).size());
		Page<Cash> tPage = new PageImpl<Cash>(this.getCashList(type).subList((page-1)*size, toindex));
		return tPage;
	}

	@Override
	public Page<Cash> findAllCashByCompanyType(Company comp, int type, int page, int size) {
		int toindex = Math.min(page*size, this.getCashList(comp, type).size());
		Page<Cash> tPage = new PageImpl<Cash>(this.getCashList(comp, type).subList((page-1)*size, toindex));
		return tPage;
	}
	
	@Override
	public long count(){
		return super.count();
	}

}
