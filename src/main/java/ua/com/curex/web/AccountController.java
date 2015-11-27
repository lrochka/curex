/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.curex.domain.Account;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.Role;
import ua.com.curex.service.AccountService;
import ua.com.curex.service.CompanyService;
import ua.com.curex.service.RoleService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller
public class AccountController {
	private static final String VN_REG_FORM = "users/registrationForm";
	private static final String VN_REG_OK = "redirect:/users/{username}.html?saved=true";
	
	@Inject private AccountService accountService;
	@Inject private CompanyService companyService;
	@Inject private RoleService roleService;
	
	//@Inject
	//@Qualifier("authenticationManager")
	//private AuthenticationManager authMgr;
	/*
	 * TODO
	 * Update size of panel and pane
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"username", "password", "confirmPassword", "firstName", "lastName", "company",
			"email","phone","role"
		});
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUsers(Model model) {
		model.addAttribute("userList",this.accountService.getAccountList());
		return "users/userList";
	}
	
	/**
	 * Returns the requested account's details view.
	 * 
	 * @param username Account username
	 * @param model model
	 * @return logical view name
	 */
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public String getAccount(@PathVariable("username") String username, Model model) throws IllegalArgumentException{
		try{
		Account account = accountService.getAccountByUsername(username);
		model.addAttribute(account);
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		return "users/user";
	}
	
	@RequestMapping(value = "/users/{username}/edit", method = RequestMethod.GET)
	public String getEditAccountForm(
			@PathVariable("username") String username,
			Model model) {
		try{
		model.addAttribute("companyList", this.companyService.getCompanyList());
		model.addAttribute("roleList", this.roleService.getRoleList());
		
		Account account = accountService.getAccountByUsername(username);
		model.addAttribute("originalAccount", account);
		AccountForm accountf = new AccountForm();
		
		accountf.setCompany(account.getCompany().getId());
		accountf.setEmail(account.getEmail());
		accountf.setFirstName(account.getFirstName());
		accountf.setLastName(account.getLastName());
		accountf.setPhone(account.getPhone());
		String roles = "";
		for(Role r : account.getRoles()) roles=roles + r.getCode() +" ";
		accountf.setRole(roles.split(" "));
		accountf.setUsername(account.getUsername());
		
		model.addAttribute("account", accountf);
		
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}catch(NullPointerException e){
			model.addAttribute("error", e.getMessage());
			return "accessdenied";
		}
		
		return "users/editAccountForm";
	}
	
	@RequestMapping(value = "/users/{username}/edit", method = RequestMethod.POST)
	public String putAccount(
			@PathVariable("username") String username,
			@ModelAttribute("account") @Valid AccountForm account,
			BindingResult result,
			Model model) {

		if (result.hasErrors()) {
			//log.debug("Submitted account has validation errors");
			model.addAttribute("companyList", this.companyService.getCompanyList());
			model.addAttribute("roleList", this.roleService.getRoleList());
			model.addAttribute("originalAccount", this.accountService.getAccountByUsername(account.getUsername()));
			return "users/editAccountForm";
		}
		
		Account accountO = new Account();
		accountO = accountService.getAccountByUsername(account.getUsername());
		
		//log.debug("Account validated; updating account's info");
		accountO.setFirstName(account.getFirstName());
		accountO.setLastName(account.getLastName());
		accountO.setEmail(account.getEmail());
		accountO.setPhone(account.getPhone());
		accountO.setCompany(this.companyService.getCompanyById(account.getCompany()));
		
		convertPasswordError(result);
		convertRoleError(result);
		accountService.setAccountInfo(accountO, account.getRole(), account.getPassword(), result);
		
		if (result.hasErrors()) {
			model.addAttribute("account", account);
			model.addAttribute("companyList", this.companyService.getCompanyList());
			model.addAttribute("roleList", this.roleService.getRoleList());
			model.addAttribute("originalAccount", this.accountService.getAccountByUsername(account.getUsername()));
			return "users/editAccountForm";
		}
		model.addAttribute("username", accountO.getUsername());
		return  VN_REG_OK;
	}

	@RequestMapping(value = "/users/new/post", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("account", new AccountForm());
		model.addAttribute("companyList", this.companyService.getCompanyList());
		model.addAttribute("roleList", this.roleService.getRoleList());
		
		return VN_REG_FORM;
	}
	
	@RequestMapping(value = "/users/new/post", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("account") @Valid AccountForm form,
			BindingResult result, Model model) {
		
		convertPasswordError(result);
		convertRoleError(result);
		String password = form.getPassword();
		accountService.registerAccount(toAccount(form, this.companyService.getCompanyById(form.getCompany())), form.getRole(),  password, result);
		
		/*Authentication authRequest =
			new UsernamePasswordAuthenticationToken(form.getUsername(), password);
		Authentication authResult = authMgr.authenticate(authRequest);
		SecurityContextHolder.getContext().setAuthentication(authResult); 
		*/
		
		model.addAttribute("companyList", this.companyService.getCompanyList());
		model.addAttribute("roleList", this.roleService.getRoleList());
		model.addAttribute("username", form.getUsername());
		
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}
	
	private static void convertPasswordError(BindingResult result) {
		// Map class-level Hibernate error message to field-level Spring error message.
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("account.password.mismatch.message".equals(msg)) {
				// Don't show if there's already some other error message.
				if (!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.password", new String[] { "error.password" }, "Пароли не совпадают!");
				}
			}
		}
	}
	
	private static void convertRoleError(BindingResult result) {
		// Map class-level Hibernate error message to field-level Spring error message.
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("account.role.isnull.message".equals(msg)) {
				// Don't show if there's already some other error message.
				if (!result.hasFieldErrors("role")) {
					result.rejectValue("role", "error.role", new String[] { "error.role" }, "Добавьте хотя бы одну роль!");
				}
			}
		}
	}
	
	private static Account toAccount(AccountForm form, Company company) {
		Account account = new Account();
		account.setUsername(form.getUsername());
		account.setFirstName(form.getFirstName());
		account.setLastName(form.getLastName());
		account.setEmail(form.getEmail());
		account.setCompany(company);
		account.setPhone(form.getPhone());
		account.setEnabled(true);
		return account;
	}
}
