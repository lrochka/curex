/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Cash;
import ua.com.curex.domain.Client;
/**
 * @author Irochka
 */
public interface CashDao {
	public Cash findByCompanyCurrencyDateType(Company company, Currency currency, Date date, int type);
	
	public Page<Cash> findAllCashByType(int type, int page,int size);
	
	public Page<Cash> findAllCashByCompanyType(Company comp, int type, int page,int size);
	
	public void updateCash(Cash cash);
	
	public List<Cash> getCashList();

	public List<Cash> getCashList(Company company);
	
	public List<Cash> getCashList(Company company, Date date);
	
	public List<Cash> getCashList(Company company, Date bdate, Date edate);
	
	public long count();
	
}
