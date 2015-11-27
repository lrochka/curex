package ua.com.curex.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.sql.Date;
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
import ua.com.curex.domain.Document;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.CompanyService;
import ua.com.curex.service.DocumentService;
import ua.com.curex.web.helper.FilterHelper;
/**
 * @author Irochka 
 */
@Controller
public class DocumentController {
	@Inject private CompanyService companyService;
	@Inject private DocumentService documentService;
	private String formatDate = "dd-MM-yyyy";
	
	@RequestMapping(value = "/documentListIN", method = GET)
	public
	@ResponseBody
	ViewPage<Document> listDocIN(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
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
			/*List<Document> docs = getJdbcTemplate().query(
	                "Select * from ordercustomers " + filterQuery,                
	                       new BeanPropertyRowMapper(Document.class)
	                       , filterParam.toArray()
	                );*/  
			if (filter != null)	return new ViewPage<Document>(this.documentService.findAllDocumentByTypeFilter(filterQuery, filterParam, 0, page, size),page,size, this.documentService.count(0));
			return new ViewPage<Document>(this.documentService.findAllDocumentByType(0, page, size),page,size, this.documentService.count(0));
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Document>(this.documentService.findAllDocumentByCompanyType(comp, 0, page, size),page,size, this.documentService.count(comp, 0));
	}
	
	@RequestMapping(value = "/documentListOUT", method = GET)
	public
	@ResponseBody
	ViewPage<Document> listDocOUT(@RequestParam(value = "pq_curpage", required = false, defaultValue = "1") int page,
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
			/*List<Document> docs = getJdbcTemplate().query(
	                "Select * from ordercustomers " + filterQuery,                
	                       new BeanPropertyRowMapper(Document.class)
	                       , filterParam.toArray()
	                );*/  
			if (filter != null)	return new ViewPage<Document>(this.documentService.findAllDocumentByTypeFilter(filterQuery, filterParam, 1, page, size),page,size, this.documentService.count(1));
			return new ViewPage<Document>(this.documentService.findAllDocumentByType(1, page, size),page,size, this.documentService.count(1));
			}
		Company comp = this.companyService.getCompanyById(((UserDetailsAdapter) authn.getPrincipal()).getAccount().getCompany().getId());
		return new ViewPage<Document>(this.documentService.findAllDocumentByCompanyType(comp, 1, page, size),page,size, this.documentService.count(comp, 1));
	}
	
	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String getDocument(Model model){
		return "documents/documentList";
	}
	
	@RequestMapping(value = "/appDocUpdate", method = RequestMethod.GET)
	public @ResponseBody String editDocument(@RequestParam int id, @RequestParam String namecompany,
			@RequestParam String typename, @RequestParam double sum, @RequestParam String comment, @RequestParam String codecurrency, @RequestParam Date ddate, @RequestParam int type, HttpServletRequest request){
		String mss = "no rignts";
		if (documentService.exists((long)id)) {
		    //doc.setId(id);
			int ttype=0;
			if (typename.compareTo("Расход")==0) ttype=1;
			SecurityContext securityCtx = SecurityContextHolder.getContext();
			Authentication authn = securityCtx.getAuthentication();
			if (((UserDetailsAdapter) authn.getPrincipal()).getAccount().hasRole("ROLE_CASH")) { 
				mss = documentService.execUpdateDoc(namecompany, codecurrency, ((UserDetailsAdapter) authn.getPrincipal()).getAccount().getUsername(), ttype, ddate, sum, comment, (long)id);
			}
		    return "{\"result\": \"success\", \"mess\": \""+mss+"\"}";
	    }
		return "{\"result\": \"error\", \"mess\": \""+mss+"\"}";
	}
}