/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.ReflectionUtils;

import ua.com.curex.dao.Dao;
import ua.com.curex.domain.Client;
import ua.com.curex.domain.Company;

/**
 * @author Irochka
 */
public abstract class AbstractHbnDao<T extends Object> implements Dao<T> {
	@Inject private SessionFactory sessionFactory;
	
	private Class<T> domainClass;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
	    if (domainClass == null) {
	    	ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
	        this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
	    }
	    return domainClass;
	}
	
	private String getDomainClassName() { return getDomainClass().getName(); }
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#create(java.lang.Object)
	 */
	@Override
	public void create(T t) {
		
		// If there's a setDateCreated() method, then set the date.
		Method method = ReflectionUtils.findMethod(
				getDomainClass(), "setDateCreated", new Class[] { Date.class });
		if (method != null) {
			try {
				method.invoke(t, new Date());
			} catch (Exception e) {
				// Ignore any exception here; simply abort the setDate() attempt
			}
		}
		
		getSession().save(t);
	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#get(java.io.Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(getDomainClass(), id);
	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#load(java.io.Serializable)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getDomainClass(), id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession()
			.createQuery("from " + getDomainClassName())
			.list();
	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#update(java.lang.Object)
	 */
	@Override
	public void update(T t) { getSession().update(t); }
	
	@Override
	public void merge(T t) { getSession().merge(t);	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T t) { getSession().delete(t); }
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#deleteById(java.io.Serializable)
	 */
	@Override
	public void deleteById(Serializable id) { delete(load(id)); }
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#deleteAll()
	 */
	@Override
	public void deleteAll() {
		getSession()
			.createQuery("delete " + getDomainClassName())
			.executeUpdate();
	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#count()
	 */
	@Override
	public long count() {
		return (Long) getSession()
			.createQuery("select count(*) from " + getDomainClassName())
			.uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see ua.com.curex.dao.Dao#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(Serializable id) { return (get(id) != null); }
	
	public Page<T> findAll(int page,int size){
		int toindex = Math.min(page*size, this.getAll().size());
		Page<T> tPage = new PageImpl<T>(this.getAll().subList((page-1)*size, toindex));
		//Page<T> tPage = new PageImpl<T>(this.getAll(), page, this.getAll().size());
		return tPage;
	}
	/*
	@Override
	public Page<T> findAll(Pageable page){
		Page<T> tPage = new PageImpl<T>(this.getAll(), page, this.getAll().size());
		return tPage;
	}*/
}
