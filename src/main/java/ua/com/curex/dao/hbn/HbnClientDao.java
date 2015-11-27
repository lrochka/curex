/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao.hbn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.com.curex.dao.ClientDao;
import ua.com.curex.domain.Client;
import ua.com.curex.domain.ClientSource;
import ua.com.curex.domain.ClientStatus;

/**
 * @author Irochka
 */
@Repository
public class HbnClientDao extends AbstractHbnDao<Client> implements ClientDao {
    private static final Logger log = LoggerFactory.getLogger(HbnAccountDao.class);
    /*private static final String DELETE_CLIENTCONTACT_SQL =
    		"delete from clncontacts where nclient = ? and nclntypecontact = ?";
    private static final String INSERT_CLIENTCONTACT_SQL =
    		"INSERT INTO `clncontacts`(`nclient`,`nclntypecontact`,`svalue`,`ddate_created`)VALUES(?,?,?,CURRENT_TIMESTAMP)";
    private static final String SELECT_CLIENTCONTACT_SQL =
    		"select nclntypecontact, svalue from clncontacts where nclient = ?";
    private static final String UPDATE_CLIENTCONTACT_SQL =
    		"update clncontacts set svalue=?  where nclient = ? and nclntypecontact = ?";*/
    private static final String SELECT_CLIENTS_BY_COMPANY_SQL =
    		"select * from clients where ncompany = ?";

    @Inject private JdbcTemplate jdbcTemplate;

    public void register(Client client) {
		log.debug("Creating client: {}", client);
		//attention this codes are from DataBase tables clninsrc and clnstats they must exist!
		client.setClnSrc(findByCodeClientSource("none"));
		client.setClnStats(findByCodeClientStatus("reliable"));
		
		create(client);
		
		//log.debug("Creating ClientContact");
		//for(ClientContact c : client.getClnContacts()) jdbcTemplate.update(INSERT_CLIENTCONTACT_SQL, client.getId(), c.getClnTypeContact().getId(), c.getValue());
    }
    
    public void updating(Client client){
		log.debug("Updating client: {}", client);
		if (client.getAddContact().equals(null)) client.setAddContact("");
		update(client);
		/*
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CLIENTCONTACT_SQL, client.getId());
		
		for(Map<String, Object> row : rows) {
			
			ClientContact clnContact = new ClientContact();
			
			ClientTypeContact clnTypeContact = findByIdClientTypeContact((Integer.toUnsignedLong((Integer)(row.get("nclntypecontact")))));
			
			clnContact.setClnTypeContact(clnTypeContact);
			clnContact.setValue((String)row.get("svalue"));
			boolean check=true;
			for(ClientContact c : client.getClnContacts()){
				if(c.getClnTypeContact().equals(clnContact.getClnTypeContact())){
					log.debug("Updating ClientContact");
					jdbcTemplate.update(
							UPDATE_CLIENTCONTACT_SQL, 
							clnContact.getValue(), 
							client.getId(),
							clnContact.getClnTypeContact().getId());
					check=false;
				}
			}
			if (check) {
				log.debug("Creating ClientContact");
				jdbcTemplate.update(
					INSERT_CLIENTCONTACT_SQL, 
					client.getId(),
					clnContact.getClnTypeContact().getId(),
					clnContact.getValue());
				}
			}*/
    }
    
    public Page<Client> findAllClient(int page, int size){	
    	return findAll(page,size); 
    }
    
    public Client findByPhone(String phone) {
		
		Client client = (Client) getSession()
				.getNamedQuery("clients.byPhone")
				.setParameter("phone", phone)
				.uniqueResult();
		/*
		Collection<ClientContact> clnContacts = new HashSet<ClientContact>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CLIENTCONTACT_SQL, client.getId());
		
		for(Map<String, Object> row : rows) {
			
			ClientContact clnContact = new ClientContact();
			
			ClientTypeContact clnTypeContact = findByIdClientTypeContact((Integer.toUnsignedLong((Integer)(row.get("nclntypecontact")))));
			
			clnContact.setClnTypeContact(clnTypeContact);
			clnContact.setValue((String)row.get("svalue"));
			
			clnContacts.add(clnContact);
			}
		client.setClnContacts(clnContacts);*/
		return client;
	}
    /*
    public ClientTypeContact findByIdClientTypeContact(long id) {
		
		return (ClientTypeContact) getSession()
				.getNamedQuery("clntypecontact.byId")
				.setParameter("id", id)
				.uniqueResult();
    }*/
	
    public ClientSource findByCodeClientSource(String code) {
		
		return (ClientSource) getSession()
				.getNamedQuery("clninsrc.byCode")
				.setParameter("code", code)
				.uniqueResult();
    }
    
    public ClientStatus findByCodeClientStatus(String code) {
		
		return (ClientStatus) getSession()
				.getNamedQuery("clnstats.byCode")
				.setParameter("code", code)
				.uniqueResult();
    }
    
	public List<Client> getClientList(){
		return this.getAll();
	}
	
	public List<Client> getClientListByCompany(Long id){
		
		List<Client> clientList = new ArrayList<Client>();
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SELECT_CLIENTS_BY_COMPANY_SQL, id);
		
		for(Map<String, Object> row : rows) {
			
			Client client = findByPhone((String)row.get("sphone"));
			
			clientList.add(client);
			}
		
		return clientList;
	}
	
}
