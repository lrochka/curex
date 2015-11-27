/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.util.List;

import ua.com.curex.domain.Currency;

/**
 * @author Irochka
 */

public interface CurrencyService {
	
	public List<Currency> getCurrencyList();
	/**
	 * Returns the requested currency or <code>null</code> if no such currency exists.
	 * 
	 * @param alpha
	 *            alpha data
	 * @return the requested currency, or <code>null</code> if it doesn't exist
	 */
	public Currency getCurrencyByAlpha(String alpha);
	
	public byte[] loadImage(String alpha);
}
