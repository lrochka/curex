/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import org.springframework.data.domain.Page;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Deal;
/**
 * @author Irochka
 */
public interface DealDao {
	public Deal findByCompanyCurrencyDate(Company company, Currency currency, Date date);
	
	public void createDeal(Deal deal);
	
	public void updateDeal(Deal deal);
	
	public List<Deal> getDealList();

	public List<Deal> getDealList(Company company);
	
	// public List<Deal> geDealList(Company company, Date date);
	
	// public List<Deal> getDealList(Company company, Date bdate, Date edate);

	public Page<Deal> findAllDealByDealdrct(int dealdrct, int page, int size);

	public Page<Deal> findAllDealByCompanyDealdrct(Company comp, int dealdrct, int page, int size);

	public long count();
	
	public long count(int dealdrct);
	
	public long count(Company comp,int dealdrct);
	
	public boolean exists(Serializable id);
	
	public Deal findByID(long id);
	
	public void update(Deal deal);

	public Page<Deal> findAllDealByTypeFiler(String filterQuery, 
			List<Object> filterParam, int dealdrct, int page, int size);
	
	public String execUpdateDeal(long id, 
			String compCode,
			Date date,
			String clientphone,
			int stagedeal,
			Time plantime,
			Time facttime,
			int dealdrct,
			String curfrom,
			String curto,
			double currateplan,
			int isbribe,
			double sumbribe,
			double sumfrom,
			String userName,  
			String comment, 
			long bribe, 
			double curratebribe) throws SQLException;
	
	public String execCreateDeal(String compCode,
			Date date,
			String clientphone,
			int stagedeal,
			Time plantime,
			Time facttime,
			int dealdrct,
			String curfrom,
			String curto,
			double currateplan,
			int isbribe,
			double sumbribe,
			double sumfrom,
			String userName,  
			String comment, 
			long bribe, 
			double curratebribe) throws SQLException;
	
}

