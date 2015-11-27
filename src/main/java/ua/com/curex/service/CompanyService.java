/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.util.List;

import org.springframework.validation.Errors;

import ua.com.curex.domain.Client;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;

/**
 * @author Irochka
 */

public interface CompanyService {
	
	public List<Company> getCompanyList();
	
	public List<Currency> getCompanyCurrencyList(Long id);
	/**
	 * Returns the requested company or <code>null</code> if no such company exists.
	 * 
	 * @param id
	 *            id data
	 * @return the requested company, or <code>null</code> if it doesn't exist
	 */
	public Company getCompanyById(Long id);
	
	public void createCompany(Company company);
	
	public void updateCompany(Company c);

	public Company getCompanyByCode(String code);
}
