/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;

import java.util.List;

/**
 * @author Irochka
 */

public interface CompanyDao {

	public Company findById(Long id);
	
	public void updateCompany(Company c);
	
	public void createCompany(Company c);
	
	public List<Company> getCompanyList();
	
	public List<Currency> getCompanyCurrencyList(Long id);

	public Company findByCode(String code);
}
