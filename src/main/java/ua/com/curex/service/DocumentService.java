/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import ua.com.curex.domain.Document;
import ua.com.curex.domain.Company;

/**
 * @author Irochka
 */
public interface DocumentService {
	
	public Page<Document> findAllDocumentByType(int type, int page,int size);
	
	public Page<Document> findAllDocumentByTypeFilter(String filterQuery, List<Object> filterParam, int type, int page,int size);
	
	public Page<Document> findAllDocumentByCompanyType(Company comp, int type, int page,int size);
	
	public long count();
	
	public boolean exists(Serializable id);
	
	public Document getDocumentById(long id);

	public long count(int type);
	
	public void update(Document doc);
	
	public String execUpdateDoc(String compCode, String curCode, String userName, int type, Date date, double sum, String comment, long id);
	
	public String execCreateDoc(String compCode, String curCode, String userName, int type, Date date, double sum, String comment);

	long count(Company comp, int type);
}
