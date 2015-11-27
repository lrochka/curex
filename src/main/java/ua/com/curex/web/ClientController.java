package ua.com.curex.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.curex.dao.DaoClient;
import ua.com.curex.domain.Client;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.ClientService;
import ua.com.curex.service.CompanyService;
/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller
//@RequestMapping(value = "/clients")
public class ClientController {
	
	@Inject private ClientService clientService;
	@Inject private CompanyService companyService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
				"phone", "name", "company", "addContact"});
	}
	
	
	@RequestMapping(value = "/clientList", method = GET)
	public
	@ResponseBody
	ViewPage<Client> listClients(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size) {
		return new ViewPage<Client>(this.clientService.findAll(page,size),page,size,this.clientService.getClientList().size());
	}
	
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public String getClients(Model model){
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) { 
			model.addAttribute("clientList",this.clientService.getClientList());
			}
		else {
			model.addAttribute("clientList",this.clientService.getClientListByCompany(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId()));
		}
		return "clients/clientList";
	}
	
	@RequestMapping(value = "/clients/{phone}", method = RequestMethod.GET)
	public String getClient(@PathVariable("phone") String phone, Model model) throws IllegalArgumentException{
		try{
			Client client = clientService.getClientByPhone(phone);
			model.addAttribute(client);
			SecurityContext securityCtx = SecurityContextHolder.getContext();
			Authentication authn = securityCtx.getAuthentication();
			if (!((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) {
				if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId()!=client.getCompany().getId()){
					model.addAttribute("error", "У Вас нет прав просматривать данного клента!");
					return "accessdenied";
				}
			}
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "clients/client";
	}
	
	@RequestMapping(value = "/clients/{phone}/edit", method = RequestMethod.GET)
	public String getEditClientForm(
			@PathVariable("phone") String phone,
			Model model) {
		
		try{
		
		model.addAttribute("companyList", this.companyService.getCompanyList());
		Client client = new Client();
			
		client = clientService.getClientByPhone(phone);
			
		model.addAttribute("originalClient",client);
			
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (!((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) {
			if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId()!=client.getCompany().getId()){
				model.addAttribute("error", "У Вас нет прав редактировать данного клента!");
				return "accessdenied";
			}
		}
		
		ClientForm clientf = new ClientForm();
		
		clientf.setCompany(client.getCompany().getId());
		clientf.setName(client.getName());
		clientf.setPhone(client.getPhone());
		clientf.setAddContact(client.getAddContact());
		
		model.addAttribute("client", clientf);
		
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}catch(NullPointerException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		
		return "clients/editClientForm";
	}

	@RequestMapping(value = "/clients/{phone}/edit", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String putClient(
			@PathVariable("phone") String username,
			@ModelAttribute("client") @Valid ClientForm client,
			BindingResult result,
			Model model) {

		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
	    
		if (result.hasErrors()) {
			//log.debug("Submitted account has validation errors");
			model.addAttribute("companyList", this.companyService.getCompanyList());
			model.addAttribute("originalClient", this.clientService.getClientByPhone(client.getPhone()));
			return "clients/editClientForm";
		}
		
		Client clientO = clientService.getClientByPhone(client.getPhone());

		clientO.setName(client.getName());
		clientO.setCompany(this.companyService.getCompanyById(client.getCompany()));
		clientO.setAddContact(client.getAddContact());
		clientO.setAuthor(((UserDetailsAdapter) authn.getPrincipal()).getAccount());
		//clientO.setAuthor((Account) authn.getPrincipal());
		
		//log.debug("Client validated; updating client's info");
		clientService.updateClient(clientO, result);
		
		if (result.hasErrors()) {
			model.addAttribute("client", client);
			model.addAttribute("companyList", this.companyService.getCompanyList());
			model.addAttribute("originalClient", this.clientService.getClientByPhone(client.getPhone()));
			return "clients/editClientForm";
		}
		
		return  "redirect:/clients/{phone}.html?saved=true";
	}
	
	@RequestMapping(value = "/clients/new/post", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("client", new ClientForm());
		model.addAttribute("companyList", this.companyService.getCompanyList());
		
		return "clients/newClientForm";
	}
	
	@RequestMapping(value = "/clients/new/post", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("client") @Valid ClientForm form,
			BindingResult result, Model model) {
		
		Client client = toClient(form, this.companyService.getCompanyById(form.getCompany()));
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		client.setAuthor(((UserDetailsAdapter) authn.getPrincipal()).getAccount());
				//this.accountService.getAccountByUsername((String)));
		this.clientService.createClient(client, result);
		
		model.addAttribute("companyList", this.companyService.getCompanyList());
		model.addAttribute("phone",client.getPhone());
		
		return (result.hasErrors() ? "clients/newClientForm" : "redirect:/clients/{phone}.html?saved=true");
	}
	
	private static Client toClient(ClientForm form, Company company) {
		Client client = new Client();
		client.setName(form.getName());
		client.setCompany(company);
		client.setPhone(form.getPhone());
		client.setAddContact(form.getAddContact());
		return client;
	}
}
