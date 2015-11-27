/* 
 * Copyright (c) 2015
 */

package ua.com.curex.service.impl;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.inject.Inject;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.service.CompanyService;
import ua.com.curex.dao.CompanyDao;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService  {
	
	@Inject private CompanyDao companyDao;
	
	public List<Company> getCompanyList() {
        return companyDao.getCompanyList();
    }
	
	public List<Currency> getCompanyCurrencyList(Long id) {
        return companyDao.getCompanyCurrencyList(id);
    }	
	public Company getCompanyById(Long id){
		Company company = companyDao.findById(id);
		if (company!=null) Hibernate.initialize(company.getCurrencies());
		return company;	
	}
	
	@Transactional(readOnly = false)
	public void updateCompany(Company c){
		companyDao.updateCompany(c);
	}
	
	@Transactional(readOnly = false)
	public void createCompany(Company c){
		companyDao.createCompany(c);
	}

	@Override
	public Company getCompanyByCode(String code) {
		Company company = companyDao.findByCode(code);
		if (company!=null) Hibernate.initialize(company.getCurrencies());
		return company;	
	}
	
}
