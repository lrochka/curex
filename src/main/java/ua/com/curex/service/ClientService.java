/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import ua.com.curex.domain.Client;

/**
 * @author Irochka
 */
public interface ClientService {
	boolean createClient(Client client, Errors errors);
	//boolean createClient(Client client, String[] clnContactStr, Errors errors);
	
	public List<Client> getClientList();
	
	public List<Client> getClientListByCompany(Long id);
	
	public Page<Client> findAll(int page,int size);
	
	public void updateClient(Client client, Errors errors);
	//public void updateClient(Client client, String[] clnContactStr, Errors errors);
	
	Client getClientByPhone(String phone);
}
