/* 
 * Copyright (c) 2015
 */
package ua.com.curex.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import ua.com.curex.dao.ClientDao;
import ua.com.curex.domain.Client;
import ua.com.curex.service.ClientService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Inject private ClientDao clientDao;
	
	@Override
	public Page<Client> findAll(int page, int size){
		return clientDao.findAllClient(page,size);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean createClient(Client client, Errors errors) {
		//public boolean createClient(Client client, String[] clnContactStr, Errors errors)
		
		validatePhone(client.getPhone(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			/*Collection<ClientContact> clnContacts = new HashSet<ClientContact>();
			for(String c : clnContactStr) {
				ClientContact clnContact = new ClientContact();
				clnContact.setClnTypeContact(clientDao.findByIdClientTypeContact(Integer.toUnsignedLong(Integer.getInteger(c.split(";")[0]))));
				clnContact.setValue(c.split(";")[1]);
				clnContacts.add(clnContact);
				}
			
			client.setClnContacts(clnContacts);*/
			clientDao.register(client);
		}
		
		return valid;
	}

	@Override
	public List<Client> getClientList() {
		return clientDao.getClientList();
	}

	@Override
	public List<Client> getClientListByCompany(Long id) {
		return clientDao.getClientListByCompany(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateClient(Client client, Errors errors) {
		/*Collection<ClientContact> clnContacts = new HashSet<ClientContact>();
		for(String c : clnContactStr) 
			{
			ClientContact clnContact = new ClientContact();
			clnContact.setClnTypeContact(clientDao.findByIdClientTypeContact(Integer.toUnsignedLong(Integer.getInteger(c.split(";")[0]))));
			clnContact.setValue(c.split(";")[1]);
			clnContacts.add(clnContact);
			}
		
		client.setClnContacts(clnContacts);*/
		clientDao.updating(client);
	}

	@Override
	public Client getClientByPhone(String phone) {
		Client client = clientDao.findByPhone(phone);
		return client;
	}
	
	private void validatePhone(String phone, Errors errors) {
		if (clientDao.findByPhone(phone) != null) {
			log.debug("Validation failed: duplicate username");
			errors.rejectValue("phone", "error.duplicate", new String[] { phone }, "Этот телефон уже используется!");
		}
	}

}
