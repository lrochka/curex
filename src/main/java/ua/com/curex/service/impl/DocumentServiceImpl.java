/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service.impl;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.curex.dao.DocumentDao;
import ua.com.curex.domain.Document;
import ua.com.curex.domain.Company;
import ua.com.curex.service.DocumentService;

/**
 * @author Irochka
 */
@Service
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {
private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Inject private DocumentDao documentDao;
	
	@Transactional(readOnly = false)
	public String execUpdateDoc(String compCode, String curCode, String userName, int type, Date date, double sum, String comment, long id){
		try{
			String res = documentDao.execUpdateDoc(compCode, curCode, userName, type, date, sum, comment, id);
		log.debug("app_documents_update result: {}", res);
		return res;
		}
		catch(SQLException ex){
			log.debug("app_documents_update error: {}", ex.getMessage());
			return ex.getMessage();
		}
	}
	
	@Transactional(readOnly = false)
	public String execCreateDoc(String compCode, String curCode, String userName, int type, Date date, double sum, String comment){
		try{
			String res = documentDao.execCreateDoc(compCode, curCode, userName, type, date, sum, comment);
		log.debug("app_documents_create result: {}", res);
		return res;
		}
		catch(SQLException ex){
			log.debug("app_documents_create error: {}", ex.getMessage());
			return ex.getMessage();
		}
	}
	
	@Override
	public Page<Document> findAllDocumentByType(int type, int page, int size) {
		
		return documentDao.findAllDocumentByType(type, page, size);
	}

	@Override
	public Page<Document> findAllDocumentByCompanyType(Company comp, int type, int page, int size) {
		return documentDao.findAllDocumentByCompanyType(comp, type, page, size);
	}

	@Override
	public long count() {
		
		return documentDao.count();
	}
	
	@Override
	public long count(int type) {
		
		return documentDao.count(type);
	}
	
	@Override
	public long count(Company comp, int type) {
		
		return documentDao.count(comp, type);
	}

	@Override
	public boolean exists(Serializable id) {
		return documentDao.exists(id);
	}

	@Override
	public Document getDocumentById(long id) {
		return documentDao.findByID(id);
	}

	@Override
	public void update(Document doc) {
		documentDao.update(doc);
	}

	@Override
	public Page<Document> findAllDocumentByTypeFilter(String filterQuery,
			List<Object> filterParam, int type, int page, int size) {
		return documentDao.findAllDocumentByTypeFiler(filterQuery, filterParam, type, page, size);
	}

}
