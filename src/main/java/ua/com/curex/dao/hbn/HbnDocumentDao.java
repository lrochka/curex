/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.CurrencyDao;
import ua.com.curex.dao.DocumentDao;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Document;

/**
 * @author Irochka
 */
@Repository
public class HbnDocumentDao extends AbstractHbnDao<Document> implements DocumentDao{
	private static final Logger log = LoggerFactory.getLogger(HbnDocumentDao.class);
	
	private static final String SELECT_DOCUMENTS_BY_TYPE_SQL =
    		"select * from documents where ntype = ? order by ddate desc, ncompany, ncurrency";
	
	private static final String SELECT_DOCUMENTS_BY_COMPANY_SQL =
    		"select * from documents where ncompany = ?";

	private static final String SELECT_DOCUMENTS_BY_COMPANY_TYPE_SQL =
    		"select * from documents where ncompany = ? and ntype = ? order by ddate desc, ncompany, ncurrency";
	
	private static final String SELECT_DOCUMENTS_BY_COMPANY_DATE_SQL =
    		"select * from documents where ncompany = ? and ncurrency = ? and ddate = ?";
	
	private static final String SELECT_DOCUMENTS_BY_COMPANY_DATE_RANGE_SQL =
    		"select * from documents where ncompany = ? and ncurrency = ? and ddate between ? and ?";
	
	private static final String PROC_UPDATE_DOCUMENT = 
			"call currencyexchange.APP_DOCUMENTS_UPDATE(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String PROC_CREATE_DOCUMENT = 
			"call currencyexchange.APP_DOCUMENTS_CREATE(?, ?, ?, ?, ?, ?, ?, ?)";

	public String execUpdateDoc(String compCode, String curCode, String userName, int type, Date date, double sum, String comment, long id) throws SQLException{
		
		CallableStatement callableStatement;
		
		callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(PROC_UPDATE_DOCUMENT);
		callableStatement.setString("scompanycode", compCode);
		callableStatement.setString("scurrencycode", curCode);
		callableStatement.setString("susercode", userName);
		callableStatement.setInt("ntype", type);
		callableStatement.setDate("ddate", date);
		callableStatement.setDouble("dsum", sum);
		callableStatement.setString("scomment", comment);
		callableStatement.setLong("ndocument", id);
		callableStatement.registerOutParameter("result", java.sql.Types.VARCHAR);
		callableStatement.executeUpdate();
		String res = callableStatement.getString("result");
		
		return res;
	}
	
	
	public void createDocument(Document doc) {
		log.debug("Creating document: {}", doc);
		create(doc);
	}
	
	public Document findByID(long id){
		return (Document) getSession()
				.getNamedQuery("documents.byId")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public Document findByCompanyCurrencyDate(Company company, Currency currency, Date date){
		return (Document) getSession()
				.getNamedQuery("documents.byCompanyCurrencyDate")
				.setParameter("company", company)
				.setParameter("currency", currency)
				.setParameter("ddate", date)
				.uniqueResult();
	}

	@Inject private JdbcTemplate jdbcTemplate;
	@Inject private CurrencyDao currencyDao;
	
	public void updateDocument(Document doc){
		log.debug("Updating document: {}", doc);
		merge(doc);
	}
	
	public List<Document> getDocumentList(){
		return this.getAll();
	}

	public List<Document> getDocumentList(Company company){

		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DOCUMENTS_BY_COMPANY_SQL, company.getId());
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Document doc = findByCompanyCurrencyDate(company,currency,Date.valueOf(((Date)row.get("ddate")).toString()));
			
			docList.add(doc);
			}
		
		return docList;
	}
	
	public List<Document> getDocumentList(int type){

		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DOCUMENTS_BY_TYPE_SQL, type);
		
		for(Map<String, Object> row : rows) {
			
			Document document = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			document.setCodecurrency(document.getCurrency().getAlpha());
			document.setNamecompany(document.getCompany().getCode());
			document.setTypename();
			docList.add(document);
			}
		
		return docList;
	}
	
	public List<Document> getDocumentList(Company company, int type){

		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DOCUMENTS_BY_COMPANY_TYPE_SQL, company.getId(), type);

		for(Map<String, Object> row : rows) {
			
			Document document = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			document.setCodecurrency(document.getCurrency().getAlpha());
			document.setNamecompany(document.getCompany().getCode()+": "+document.getCompany().getName());
			document.setTypename();
			docList.add(document);
			}
		
		return docList;
	}
	
	public List<Document> getDocumentList(Company company, Date date){

		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DOCUMENTS_BY_COMPANY_DATE_SQL, company.getId(), date);
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Document doc = findByCompanyCurrencyDate(company,currency,date);
			
			docList.add(doc);
			}
		
		return docList;
	}
	
	public List<Document> getDocumentList(Company company, Date bdate,Date edate){

		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DOCUMENTS_BY_COMPANY_DATE_RANGE_SQL, company.getId(), bdate,edate);
		
		for(Map<String, Object> row : rows) {
			Currency currency = this.currencyDao.findById(Long.valueOf(((BigInteger)row.get("ncurrency")).toString()));
			Document doc = findByCompanyCurrencyDate(company,currency,Date.valueOf(((Date)row.get("ddate")).toString()));
			
			docList.add(doc);
			}
		
		return docList;
	}

	@Override
	public Page<Document> findAll(int page,int size) {
		return findAll(page,size);
	}

	@Override
	public Page<Document> findAllDocumentByType(int type, int page, int size) {
		int toindex = Math.min(page*size, this.getDocumentList(type).size());
		Page<Document> tPage = new PageImpl<Document>(this.getDocumentList(type).subList((page-1)*size, toindex));
		return tPage;
	}

	@Override
	public Page<Document> findAllDocumentByCompanyType(Company comp, int type, int page, int size) {
		int toindex = Math.min(page*size, this.getDocumentList(comp, type).size());
		Page<Document> tPage = new PageImpl<Document>(this.getDocumentList(comp, type).subList((page-1)*size, toindex));
		return tPage;
	}

	@Override
	public long count(int type) {
		return (Long) getSession()
				.createQuery("select count(*) from Document where type = "+String.valueOf(type))
				.uniqueResult();
	}
	
	@Override
	public long count(Company comp, int type) {
		return (Long) getSession()
				.getNamedQuery("documents.countByCompType")
				.setParameter("company", comp)
				.setParameter("type", type)
				.uniqueResult();
	}

	@Override
	public Page<Document> findAllDocumentByTypeFiler(String filterQuery, 
			List<Object> filterParam, int type, int page, int size) {
		log.debug("filterQuery: {}", filterQuery);
		log.debug("filterParam: {}", filterParam);
		int toindex = Math.min(page*size, this.getDocumentListFilter(filterQuery, filterParam, type).size());
		Page<Document> tPage = new PageImpl<Document>(this.getDocumentListFilter(filterQuery,  filterParam, type).subList((page-1)*size, toindex));
		return tPage;
		
	}

	private List<Document> getDocumentListFilter(String filterQuery, 
			List<Object> filterParam, int type) {
		
		List<Document> docList = new ArrayList<Document>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList( "Select * from documents " + filterQuery + " and ntype = "+ String.valueOf(type) +" order by ddate desc, ncompany, ncurrency",                
                filterParam.toArray());
		
		for(Map<String, Object> row : rows) {
			
			Document document = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			document.setCodecurrency(document.getCurrency().getAlpha());
			document.setNamecompany(document.getCompany().getCode());
			document.setTypename();
			docList.add(document);
			}
		
		return docList;
	}


	@Override
	public String execCreateDoc(String compCode, String curCode, String userName, int type, Date date, double sum,
			String comment) throws SQLException {

		CallableStatement callableStatement;
		
		callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(PROC_CREATE_DOCUMENT);
		callableStatement.setString("scompanycode", compCode);
		callableStatement.setString("scurrencycode", curCode);
		callableStatement.setString("susercode", userName);
		callableStatement.setInt("ntype", type);
		callableStatement.setDate("ddate", date);
		callableStatement.setDouble("dsum", sum);
		callableStatement.setString("scomment", comment);
		callableStatement.registerOutParameter("result", java.sql.Types.VARCHAR);
		callableStatement.executeUpdate();
		String res = callableStatement.getString("result");
		
		return res;
	}
	
}
