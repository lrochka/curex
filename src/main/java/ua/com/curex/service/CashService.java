/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import org.springframework.data.domain.Page;
import ua.com.curex.domain.Cash;
import ua.com.curex.domain.Company;

/**
 * @author Irochka
 */
public interface CashService {
	
	public Page<Cash> findAllCashByType(int type, int page,int size);
	
	public Page<Cash> findAllCashByCompanyType(Company comp, int type, int page,int size);
	
	public long count();
}