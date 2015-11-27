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

import ua.com.curex.domain.Cash;
import ua.com.curex.domain.Company;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.CashService;
import ua.com.curex.service.CompanyService;
/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller

public class CashController {
	@Inject private CompanyService companyService;
	@Inject private CashService cashService;
	
	@RequestMapping(value = "/cashList", method = GET)
	public
	@ResponseBody
	ViewPage<Cash> listCash(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size) {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) { 
			return new ViewPage<Cash>(this.cashService.findAllCashByType(1, page, size),page,size, this.cashService.count());
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Cash>(this.cashService.findAllCashByCompanyType(comp, 1, page, size),page,size, this.cashService.count());
	}
	
	@RequestMapping(value = "/cash", method = RequestMethod.GET)
	public String getCash(Model model){
		// model.addAttribute("cashLists",this.cashService.findAllCashByType(1, 1, 20).getContent());
		return "cash/cashList";
	}
	
	@RequestMapping(value = "/remainsListBGN", method = GET)
	public
	@ResponseBody
	ViewPage<Cash> listRmnBGN(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size) {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) { 
			return new ViewPage<Cash>(this.cashService.findAllCashByType(0, page, size),page,size, this.cashService.count());
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Cash>(this.cashService.findAllCashByCompanyType(comp, 0, page, size),page,size, this.cashService.count());
	}
	
	@RequestMapping(value = "/remainsListEND", method = GET)
	public
	@ResponseBody
	ViewPage<Cash> listRmnEND(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size) {
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) { 
			return new ViewPage<Cash>(this.cashService.findAllCashByType(2, page, size),page,size, this.cashService.count());
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Cash>(this.cashService.findAllCashByCompanyType(comp, 2, page, size),page,size, this.cashService.count());
	}
	
	@RequestMapping(value = "/remains", method = RequestMethod.GET)
	public String getRemains(Model model){
		// model.addAttribute("cashLists",this.cashService.findAllCashByType(1, 1, 20).getContent());
		return "remains/remainList";
	}

}
