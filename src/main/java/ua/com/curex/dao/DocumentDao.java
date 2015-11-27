/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Document;
/**
 * @author Irochka
 */
public interface DocumentDao {
	public Document findByCompanyCurrencyDate(Company company, Currency currency, Date date);
	
	public void createDocument(Document doc);
	
	public void updateDocument(Document doc);
	
	public List<Document> getDocumentList();

	public List<Document> getDocumentList(Company company);
	
	public List<Document> getDocumentList(Company company, Date date);
	
	public List<Document> getDocumentList(Company company, Date bdate, Date edate);

	public Page<Document> findAllDocumentByType(int type, int page, int size);

	public Page<Document> findAllDocumentByCompanyType(Company comp, int type, int page, int size);

	public long count();
	
	public long count(int type);
	
	public boolean exists(Serializable id);
	
	public Document findByID(long id);
	
	public void update(Document doc);

	public Page<Document> findAllDocumentByTypeFiler(String filterQuery, 
			List<Object> filterParam, int type, int page, int size);
	
	public String execUpdateDoc (String compCode, String curCode, String userName, int type, Date date, double sum, String comment, long id) throws SQLException;
	
	public String execCreateDoc (String compCode, String curCode, String userName, int type, Date date, double sum, String comment) throws SQLException;

	long count(Company comp, int type);
	
}
