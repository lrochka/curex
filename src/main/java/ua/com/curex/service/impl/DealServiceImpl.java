/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service.impl;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.curex.dao.DealDao;
import ua.com.curex.domain.Deal;
import ua.com.curex.domain.Company;
import ua.com.curex.service.DealService;

/**
 * @author Irochka
 */
@Service
@Transactional(readOnly = true)
public class DealServiceImpl implements DealService {
private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Inject private DealDao DealDao;
	
	@Transactional(readOnly = false)
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
			double curratebribe){
		try{
			String res = DealDao.execUpdateDeal(id, compCode, date, clientphone, stagedeal, plantime, facttime, dealdrct, curfrom, curto, currateplan, isbribe, sumbribe, sumfrom, userName, comment, bribe, curratebribe);
		log.debug("app_deals_update result: {}", res);
		return res;
		}
		catch(SQLException ex){
			log.debug("app_deals_update error: {}", ex.getMessage());
			return ex.getMessage();
		}
	}
	
	@Transactional(readOnly = false)
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
			double curratebribe){
		try{
			String res = DealDao.execCreateDeal(compCode, date, clientphone, stagedeal, plantime, facttime, dealdrct, curfrom, curto, currateplan, isbribe, sumbribe, sumfrom, userName, comment, bribe, curratebribe);
		log.debug("app_deals_create result: {}", res);
		return res;
		}
		catch(SQLException ex){
			log.debug("app_deals_create error: {}", ex.getMessage());
			return ex.getMessage();
		}
	}
	
	@Override
	public Page<Deal> findAllDealByDealdrct(int dealdrct, int page, int size) {
		
		return DealDao.findAllDealByDealdrct(dealdrct, page, size);
	}

	@Override
	public Page<Deal> findAllDealByCompanyDealdrct(Company comp, int dealdrct, int page, int size) {
		return DealDao.findAllDealByCompanyDealdrct(comp, dealdrct, page, size);
	}

	@Override
	public long count() {
		
		return DealDao.count();
	}
	
	@Override
	public long count(int dealdrct) {
		
		return DealDao.count(dealdrct);
	}

	@Override
	public long count(Company comp, int dealdrct) {
		
		return DealDao.count(comp, dealdrct);
	}

	@Override
	public boolean exists(Serializable id) {
		return DealDao.exists(id);
	}

	@Override
	public Deal getDealById(long id) {
		return DealDao.findByID(id);
	}

	@Override
	public void update(Deal doc) {
		DealDao.update(doc);
	}

	@Override
	public Page<Deal> findAllDealByDealdrctFilter(String filterQuery,
			List<Object> filterParam, int dealdrct, int page, int size) {
		return DealDao.findAllDealByTypeFiler(filterQuery, filterParam, dealdrct, page, size);
	}

}
