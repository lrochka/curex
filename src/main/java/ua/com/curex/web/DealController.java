package ua.com.curex.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.com.curex.domain.Company;
import ua.com.curex.domain.Deal;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.CompanyService;
import ua.com.curex.service.DealService;
import ua.com.curex.web.helper.FilterHelper;
/**
 * @author Irochka
 */
@Controller
public class DealController {
	@Inject private CompanyService companyService;
	@Inject private DealService dealService;
	private String formatDate = "dd-MM-yyyy";
	
	@RequestMapping(value = "/dealListIN", method = GET)
	public
	@ResponseBody
	ViewPage<Deal> dealListIN(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size,
		      @RequestParam(value = "pq_filter",required=false) String filter) {
		
		String filterQuery = "";
		if (page==0) page++;
        List<Object> filterParam = new ArrayList<Object>();
        if (filter != null && filter.length() > 0)
        {   //filter = filter.replace("date", "ddate");
            Map filterMap = FilterHelper.deSerialize(filter, formatDate);
            filterQuery = (String) filterMap.get("query");
            filterParam = (List<Object>) filterMap.get("param");
        }
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) {
			/*List<Deal> docs = getJdbcTemplate().query(
	                "Select * from ordercustomers " + filterQuery,                
	                       new BeanPropertyRowMapper(Deal.class)
	                       , filterParam.toArray()
	                );*/  
			if (filter != null)	return new ViewPage<Deal>(this.dealService.findAllDealByDealdrctFilter(filterQuery, filterParam, 0, page, size),page,size, this.dealService.count(0));
			return new ViewPage<Deal>(this.dealService.findAllDealByDealdrct(0, page, size),page,size, this.dealService.count(0));
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Deal>(this.dealService.findAllDealByCompanyDealdrct(comp, 0, page, size),page,size, this.dealService.count(comp, 0));
	}
	
	@RequestMapping(value = "/dealListOUT", method = GET)
	public
	@ResponseBody
	ViewPage<Deal> dealListOUT(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
		      @RequestParam(value = "pq_rpp", required = false, defaultValue = "20") int size,
		      @RequestParam(value = "pq_filter",required=false) String filter) {
		
		String filterQuery = "";
		if (page==0) page++;
      List<Object> filterParam = new ArrayList<Object>();
      if (filter != null && filter.length() > 0)
      {   //filter = filter.replace("date", "ddate");
          Map filterMap = FilterHelper.deSerialize(filter, formatDate);
          filterQuery = (String) filterMap.get("query");
          filterParam = (List<Object>) filterMap.get("param");
      }
		SecurityContext securityCtx = SecurityContextHolder.getContext();
		Authentication authn = securityCtx.getAuthentication();
		if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_ADMIN")) {
			/*List<Deal> docs = getJdbcTemplate().query(
	                "Select * from ordercustomers " + filterQuery,                
	                       new BeanPropertyRowMapper(Deal.class)
	                       , filterParam.toArray()
	                );*/  
			if (filter != null)	return new ViewPage<Deal>(this.dealService.findAllDealByDealdrctFilter(filterQuery, filterParam, 1, page, size),page,size, this.dealService.count(1));
			return new ViewPage<Deal>(this.dealService.findAllDealByDealdrct(1, page, size),page,size, this.dealService.count(1));
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Deal>(this.dealService.findAllDealByCompanyDealdrct(comp, 1, page, size),page,size, this.dealService.count(comp, 1));
	}
	
	@RequestMapping(value = "/deals", method = RequestMethod.GET)
	public String getDeal(Model model){
		return "deals/dealList";
	}
	
	@RequestMapping(value = "/appDealUpdate", method = RequestMethod.GET)
	public @ResponseBody String editDeal(@RequestParam int id, @RequestParam String namecompany,
			@RequestParam String clientphone, @RequestParam String comment, @RequestParam String typename, @RequestParam String drctname, 
			@RequestParam String curfrom, @RequestParam String curto, @RequestParam Date ddate, 
			@RequestParam int stagedeal, @RequestParam Time plantime, @RequestParam(value = "facttime", required = false) Time facttime,
			@RequestParam int dealdrct, @RequestParam double currateplan, @RequestParam int isbribe, @RequestParam double sumbribe, 
			@RequestParam double sumfrom, @RequestParam long bribe, @RequestParam double curratebribe,
			HttpServletRequest request){
		String mss = "no rignts";
		if (dealService.exists((long)id)) {
		    //doc.setId(id);
			int ttype=0,drct=0;
			if (typename.compareTo("Закрыть")==0) ttype=1;
			if (drctname.compareTo("Продажа")==0) drct=1;
			SecurityContext securityCtx = SecurityContextHolder.getContext();
			Authentication authn = securityCtx.getAuthentication();
			if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_USER")) { 
				mss = dealService.execUpdateDeal(id, 
						namecompany,
						ddate,
						clientphone,
						ttype,
						plantime,
						facttime,
						drct,
						curfrom,
						curto,
						currateplan,
						isbribe,
						sumbribe,
						sumfrom,
						((UserDetailsAdapter) authn.getPrincipal()).getAccount().getUsername(),  
						comment, 
						bribe, 
						curratebribe);
			}
		    return "{\"result\": \"success\", \"mess\": \""+mss+"\"}";
	    }
		return "{\"result\": \"error\", \"mess\": \""+mss+"\"}";
	}
}