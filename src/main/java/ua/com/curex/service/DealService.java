/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.domain.Page;

import ua.com.curex.domain.Deal;
import ua.com.curex.domain.Company;

/**
 * @author Irochka
 */
public interface DealService {
	
	public Page<Deal> findAllDealByDealdrct(int dealdrct, int page,int size);
	
	public Page<Deal> findAllDealByDealdrctFilter(String filterQuery, List<Object> filterParam, int dealdrct, int page,int size);
	
	public Page<Deal> findAllDealByCompanyDealdrct(Company comp, int dealdrct, int page,int size);
	
	public long count();
	
	public boolean exists(Serializable id);
	
	public Deal getDealById(long id);

	public long count(int dealdrct);
	
	public void update(Deal doc);
	
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
			double curratebribe);
	
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
			double curratebribe);

	long count(Company comp, int dealdrct);
}