/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import ua.com.curex.domain.Currency;
import java.util.List;

/**
 * @author Irochka
 */

public interface CurrencyDao {

	public Currency findByAlpha(String alpha);
	
	public Currency findById(Long id);
	
	public List<Currency> getCurrencyList();
}
