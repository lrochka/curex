/* 
 * Copyright (c) 2015
 */

package ua.com.curex.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.inject.Inject;

import ua.com.curex.domain.Currency;
import ua.com.curex.service.CurrencyService;
import ua.com.curex.dao.CurrencyDao;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
public class CurrencyServiceImpl implements CurrencyService  {
	
	@Inject private CurrencyDao сurrencyDao;
	
	public List<Currency> getCurrencyList() {
        return сurrencyDao.getCurrencyList();
    }
	
	public Currency getCurrencyByAlpha(String alpha){
		Currency сurrency = сurrencyDao.findByAlpha(alpha);
		return сurrency;	
	}
	
	public byte[] loadImage(String alpha){
		Currency currency = сurrencyDao.findByAlpha(alpha);
		return currency.getImg();
	}
}
