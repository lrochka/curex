/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.CurrencyDao;
import ua.com.curex.domain.Currency;

/**
 * @author Irochka
 */
@Repository
public class HbnCurrencyDao extends AbstractHbnDao<Currency> implements CurrencyDao {
	
	public List<Currency> getCurrencyList(){
		return this.getAll();
	}

	public Currency findById(Long id) {
		return (Currency) getSession()
				.getNamedQuery("currencies.byId")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public Currency findByAlpha(String alpha) {
		return (Currency) getSession()
				.getNamedQuery("currencies.byAlphaCode")
				.setParameter("alpha", alpha)
				.uniqueResult();
	}

	@Override
	public Page<Currency> findAll(int page, int size) {
		return findAll(page,size);
	}

}