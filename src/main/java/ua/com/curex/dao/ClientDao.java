/* 
 * Copyright (c) 2015
 */
package ua.com.curex.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.com.curex.domain.Client;
import ua.com.curex.dao.Dao;

/**
 * @author Irochka
 */
public interface ClientDao {
	
	void register(Client client);
	
	void updating(Client client);
	
	public Client findByPhone(String phone);
	
	//public ClientTypeContact findByIdClientTypeContact(long id);
	
	public Page<Client> findAllClient(int page,int size);
	
	public List<Client> getClientList();
	
	public List<Client> getClientListByCompany(Long id);
}
