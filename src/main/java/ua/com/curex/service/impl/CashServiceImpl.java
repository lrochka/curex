/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.curex.dao.CashDao;
import ua.com.curex.domain.Cash;
import ua.com.curex.domain.Company;
import ua.com.curex.service.CashService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
public class CashServiceImpl implements CashService {
private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Inject private CashDao cashDao;
	
	@Override
	public Page<Cash> findAllCashByType(int type, int page, int size) {
		
		return cashDao.findAllCashByType(type, page, size);
	}

	@Override
	public Page<Cash> findAllCashByCompanyType(Company comp, int type, int page, int size) {
		return cashDao.findAllCashByCompanyType(comp, type, page, size);
	}

	@Override
	public long count() {
		
		return cashDao.count();
	}

}

