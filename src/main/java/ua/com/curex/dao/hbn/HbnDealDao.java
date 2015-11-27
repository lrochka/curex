/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ua.com.curex.dao.DealDao;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.Deal;

/**
 * @author Irochka
 */
@Repository
public class HbnDealDao extends AbstractHbnDao<Deal> implements DealDao{
	private static final Logger log = LoggerFactory.getLogger(HbnDealDao.class);
	
	private static final String SELECT_DEALS_BY_DEALDRCT_SQL =
    		"select * from deals where ndealdrct = ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String SELECT_DEALS_BY_COMPANY_SQL =
    		"select * from deals where ncompany = ?";

	private static final String SELECT_DEALS_BY_COMPANY_DEALDRCT_SQL =
    		"select * from deals where ncompany = ? and ndealdrct = ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String SELECT_DEALS_BY_COMPANY_DEALDRCT_CURFROM_DATE_SQL =
    		"select * from deals where ncompany = ? and ndealdrct = ? and ncurfrom = ? and ddate = ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String SELECT_DEALS_BY_COMPANY_DEALDRCT_CURTO_DATE_SQL =
    		"select * from deals where ncompany = ? and ndealdrct = ? and ncurto = ? and ddate = ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String SELECT_DEALS_BY_COMPANY_DEALDRCT_CURFROM_CURTO_DATE_SQL =
    		"select * from deals where ncompany = ? and ndealdrct = ? and ncurfrom = ? and ncurto = ? and ddate = ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String SELECT_DEALS_BY_COMPANY_DEALDRCT_CURFROM_CURTO_DATE_RANGE_SQL =
    		"select * from deals where ncompany = ? and ndealdrct = ? and ncurfrom = ? and ncurto = ? and ddate between ? and ? order by ddate desc, ncompany, ncurfrom, ncurto";
	
	private static final String PROC_UPDATE_DEAL = 
			"call APP_DEALS_UPDATE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String PROC_CREATE_DEAL = 
			"call APP_DEALS_CREATE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	@Inject private JdbcTemplate jdbcTemplate;
	@Inject private CurrencyDao currencyDao;

	public String execUpdateDeal(long id, 
								String compCode,
								Date date,
								String clientphone,
								int stagedeal,
								Time plantime,
								Time facttime,
								int dealdrct,
								String curfrom,
								String curto,
								double currateplan,
								int isbribe,
								double sumbribe,
								double sumfrom,
								String userName,  
								String comment, 
								long bribe, 
								double curratebribe) throws SQLException{
		
		CallableStatement callableStatement;
		
		callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(PROC_UPDATE_DEAL);
		callableStatement.setLong("ndeal", id);					// 1
		callableStatement.setString("scompanycode", compCode);	// 2
		callableStatement.setDate("ddate", date);				// 3
		callableStatement.setString("sclientcode", clientphone);// 4
		callableStatement.setInt("nstagedeal", stagedeal);		// 5
		callableStatement.setTime("tplantime", plantime);		// 6
		callableStatement.setTime("tfacttime", facttime);		// 7
		callableStatement.setInt("ndealdrct", dealdrct);		// 8
		callableStatement.setString("scurcodefrom", curfrom);	// 9
		callableStatement.setString("scurcodeto", curto);		// 10
		callableStatement.setDouble("dcurrate_plan", currateplan); // 11
		callableStatement.setInt("nisbribe", isbribe);			// 12
		callableStatement.setDouble("dsumbribe", sumbribe);		// 13
		callableStatement.setDouble("dsumfrom", sumfrom);		// 14
		callableStatement.setString("susercode", userName);		// 15
		callableStatement.setString("scomment", comment);		// 16
		callableStatement.setLong("nbribe", bribe);				// 17
		callableStatement.setDouble("dcurrate_bribe", curratebribe); // 18
		
		callableStatement.registerOutParameter("result", java.sql.Types.VARCHAR);
		callableStatement.executeUpdate();
		String res = callableStatement.getString("result");
		
		return res;
	}
	
	@Override
	public String execCreateDeal(String compCode,
			Date date,
			String clientphone,
			int stagedeal,
			Time plantime,
			Time facttime,
			int dealdrct,
			String curfrom,
			String curto,
			double currateplan,
			int isbribe,
			double sumbribe,
			double sumfrom,
			String userName,  
			String comment, 
			long bribe, 
			double curratebribe) throws SQLException{


		CallableStatement callableStatement;
		
		callableStatement = jdbcTemplate.getDataSource().getConnection().prepareCall(PROC_UPDATE_DEAL);
		callableStatement.setString("scompanycode", compCode);	// 1
		callableStatement.setDate("ddate", date);				// 2
		callableStatement.setString("sclientcode", clientphone);// 3
		callableStatement.setInt("nstagedeal", stagedeal);		// 4
		callableStatement.setTime("tplantime", plantime);		// 5
		callableStatement.setTime("tfacttime", facttime);		// 6
		callableStatement.setInt("ndealdrct", dealdrct);		// 7
		callableStatement.setString("scurcodefrom", curfrom);	// 8
		callableStatement.setString("scurcodeto", curto);		// 9
		callableStatement.setDouble("dcurrate_plan", currateplan); // 10
		callableStatement.setInt("nisbribe", isbribe);			// 11
		callableStatement.setDouble("dsumbribe", sumbribe);		// 12
		callableStatement.setDouble("dsumfrom", sumfrom);		// 13
		callableStatement.setString("susercode", userName);		// 14
		callableStatement.setString("scomment", comment);		// 15
		callableStatement.setLong("nbribe", bribe);				// 16
		callableStatement.setDouble("dcurrate_bribe", curratebribe); // 17
		
		callableStatement.registerOutParameter("result", java.sql.Types.VARCHAR);
		callableStatement.executeUpdate();
		String res = callableStatement.getString("result");
		
		return res;
	}
	
	public void createDeal(Deal deal) {
		log.debug("Creating Deal: {}", deal);
		create(deal);
	}
	
	public Deal findByID(long id){
		return (Deal) getSession()
				.getNamedQuery("deals.byId")
				.setParameter("id", id)
				.uniqueResult();
	}
	
	public Deal findByCompanyCurrencyDate(Company company, Currency currency, Date date){
		return (Deal) getSession()
				.getNamedQuery("deals.byCompanyCurrencyDate")
				.setParameter("company", company)
				.setParameter("currency", currency)
				.setParameter("ddate", date)
				.uniqueResult();
	}
	
	public void updateDeal(Deal deal){
		log.debug("Updating Deal: {}", deal);
		merge(deal);
	}
	
	public List<Deal> getDealList(){
		return this.getAll();
	}

	public List<Deal> getDealList(Company company){

		List<Deal> dealList = new ArrayList<Deal>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DEALS_BY_COMPANY_SQL, company.getId());
		
		for(Map<String, Object> row : rows) {
			Deal deal = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			
			deal.setCodeCurFrom(deal.getCurfrom().getAlpha());
			deal.setCodeCurTo(deal.getCurto().getAlpha());
			deal.setNamecompany(deal.getCompany().getCode());
			deal.setClientphone(deal.getClient().getPhone());
			deal.setTypename();
			deal.setDrctname();
			dealList.add(deal);
			}
		
		return dealList;
	}
	
	public List<Deal> getDealList(int dealdrct){

		List<Deal> dealList = new ArrayList<Deal>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DEALS_BY_DEALDRCT_SQL, dealdrct);
		
		for(Map<String, Object> row : rows) {
			
			Deal deal = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			
			deal.setCodeCurFrom(deal.getCurfrom().getAlpha());
			deal.setCodeCurTo(deal.getCurto().getAlpha());
			deal.setNamecompany(deal.getCompany().getCode());
			deal.setClientphone(deal.getClient().getPhone());
			deal.setTypename();
			deal.setDrctname();
			dealList.add(deal);
			}
		
		return dealList;
	}
	
	public List<Deal> getDealList(Company company, int dealdrct){

		List<Deal> dealList = new ArrayList<Deal>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_DEALS_BY_COMPANY_DEALDRCT_SQL, company.getId(), dealdrct);

		for(Map<String, Object> row : rows) {

			Deal deal = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			
			deal.setCodeCurFrom(deal.getCurfrom().getAlpha());
			deal.setCodeCurTo(deal.getCurto().getAlpha());
			deal.setNamecompany(deal.getCompany().getCode());
			deal.setClientphone(deal.getClient().getPhone());
			deal.setTypename();
			deal.setDrctname();
			dealList.add(deal);
			}
		
		return dealList;
	}
	
	@Override
	public Page<Deal> findAll(int page,int size) {
		return findAll(page,size);
	}

	@Override
	public Page<Deal> findAllDealByDealdrct(int dealdrct, int page, int size) {
		int toindex = Math.min(page*size, this.getDealList(dealdrct).size());
		Page<Deal> tPage = new PageImpl<Deal>(this.getDealList(dealdrct).subList((page-1)*size, toindex));
		return tPage;
	}

	@Override
	public Page<Deal> findAllDealByCompanyDealdrct(Company comp, int dealdrct, int page, int size) {
		int toindex = Math.min(page*size, this.getDealList(comp, dealdrct).size());
		Page<Deal> tPage = new PageImpl<Deal>(this.getDealList(comp, dealdrct).subList((page-1)*size, toindex));
		return tPage;
	}

	@Override
	public long count(int dealdrct) {
		return (Long) getSession()
				.createQuery("select count(*) from Deal where dealdrct = "+String.valueOf(dealdrct))
				.uniqueResult();
	}
	
	@Override
	public long count(Company comp, int dealdrct) {
		return (Long) getSession()
				.getNamedQuery("deals.countByCompanyDealdrct")
				.setParameter("company", comp)
				.setParameter("dealdrct", dealdrct)
				//.createQuery("select count(*) from Deal where dealdrct = ? and "+String.valueOf(dealdrct))
				.uniqueResult();
	}

	@Override
	public Page<Deal> findAllDealByTypeFiler(String filterQuery, 
			List<Object> filterParam, int dealdrct, int page, int size) {
		log.debug("filterQuery: {}", filterQuery);
		log.debug("filterParam: {}", filterParam);
		int toindex = Math.min(page*size, this.getDealListFilter(filterQuery, filterParam, dealdrct).size());
		Page<Deal> tPage = new PageImpl<Deal>(this.getDealListFilter(filterQuery,  filterParam, dealdrct).subList((page-1)*size, toindex));
		return tPage;
		
	}

	private List<Deal> getDealListFilter(String filterQuery, 
			List<Object> filterParam, int dealdrct) {
		
		List<Deal> dealList = new ArrayList<Deal>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList( "Select * from deals " + filterQuery + " and ndealdrct = "+ String.valueOf(dealdrct) + " order by ddate desc, ncompany, ncurfrom, ncurto",                
                filterParam.toArray());
		
		for(Map<String, Object> row : rows) {
			
			Deal deal = findByID(Long.valueOf(((BigInteger)row.get("id")).toString()));
			
			deal.setCodeCurFrom(deal.getCurfrom().getAlpha());
			deal.setCodeCurTo(deal.getCurto().getAlpha());
			deal.setNamecompany(deal.getCompany().getCode());
			deal.setClientphone(deal.getClient().getPhone());
			deal.setTypename();
			deal.setDrctname();
			dealList.add(deal);
			}
		
		return dealList;
	}
	
}