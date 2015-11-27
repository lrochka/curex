/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Currency;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.AccountService;
import ua.com.curex.service.ClientService;
import ua.com.curex.service.CompanyService;
import ua.com.curex.service.CurrencyService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller
//@RequestMapping("/company")
public class CompanyController {
	
	@Inject private CompanyService companyService;
	@Inject private ClientService clientService;
	@Inject private AccountService accountService;
	@Inject private CurrencyService currencyService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"cur"
		});
	}
	
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String getCompanies(Model model) {
		model.addAttribute("companyList",this.companyService.getCompanyList());
		return "companies/companyList";
	}
	
	@RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
	public String getCompany(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
		try{
		Company company = companyService.getCompanyById(id);
		model.addAttribute(company);
		model.addAttribute("countClients", this.clientService.getClientListByCompany(id).size());
		//model.addAttribute("clientList",this.clientService.getClientListByCompany(id));
		model.addAttribute("countUsers", this.accountService.getAccountListByCompany(id).size());
		//model.addAttribute("userList", this.accountService.getAccountListByCompany(id));
		model.addAttribute("countCurrencies",company.getCurrencies().size());
		//String arrayCurrencies = "";
		//for(Currency c : company.getCurrencies()) arrayCurrencies+=c.getAlpha()+" ";
		//model.addAttribute("arrayCurrencies",arrayCurrencies.split(" "));
		model.addAttribute("currenciesList", company.getCurrencies());
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "companies/company";
	}
	

	@RequestMapping(value = "/companies/{id}/clients", method = RequestMethod.GET)
	public String getCompanyClients(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
		try{
		Company company = companyService.getCompanyById(id);
		model.addAttribute(company);
		model.addAttribute("clientList",this.clientService.getClientListByCompany(id));
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "clients/clientList";
	}
	

	@RequestMapping(value = "/companies/{id}/currencies", method = RequestMethod.GET)
	public String getCompanyCurrencies(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
		try{
		Company company = companyService.getCompanyById(id);
		CurrencyForm cur = new CurrencyForm();
		model.addAttribute(company);
		model.addAttribute("currenciesList", company.getCurrencies());
		
		List<Currency> currAdd = new ArrayList<Currency>();
		
		//for(Currency curr : company.getCurrencies()) currAdd.add(curr);
		
		for(Currency curr : this.currencyService.getCurrencyList()) if (!company.isCurrencyExists(curr)) currAdd.add(curr);
		
		model.addAttribute("currenciesListAdd", currAdd);
		model.addAttribute("currency",cur);
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "currencies/currenciesList";
	}
	
	@RequestMapping(value = "/companies/{id}/currencies", method = RequestMethod.POST)
	public String editCompanyCurrencies(@PathVariable("id") Long id, @ModelAttribute("currency") @Valid CurrencyForm cur,
			BindingResult result, Model model) throws IllegalArgumentException{
		try{
			Currency c = this.currencyService.getCurrencyByAlpha(cur.getCur());
			Company company = companyService.getCompanyById(id);
			company.addCurrency(c);
			
			companyService.updateCompany(company);
		
			model.addAttribute(company);
			model.addAttribute("currenciesList", company.getCurrencies());
			
			List<Currency> currAdd = new ArrayList<Currency>();
			
			//for(Currency curr : company.getCurrencies()) currAdd.add(curr);
			
			for(Currency curr : this.currencyService.getCurrencyList()) if (!company.isCurrencyExists(curr)) currAdd.add(curr);
			
			model.addAttribute("currenciesListAdd", currAdd);
			model.addAttribute("currency",cur);
			}catch(IllegalArgumentException e){
				model.addAttribute("error", e.getMessage());
				return "accessdenied";
			}
		
		return "redirect:/companies/{id}/currencies.html?saved=true";
	}
	
	@RequestMapping(value = "/companies/{id}/users", method = RequestMethod.GET)
	public String getCompanyUsers(@PathVariable("id") Long id, Model model) throws IllegalArgumentException{
		try{
		Company company = companyService.getCompanyById(id);
		model.addAttribute(company);
		model.addAttribute("userList", this.accountService.getAccountListByCompany(id));
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "users/userList";
	}
	
	@RequestMapping(value = "/addDocument", method = RequestMethod.GET)
	public @ResponseBody Map<String,? extends Object> getCompanyList() throws Exception {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try{
            modelMap.put("data", companyService.getCompanyList());
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            return modelMap;
        }
    }
	/*
    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }*/
}