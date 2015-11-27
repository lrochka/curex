/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.com.curex.domain.Currency;
import ua.com.curex.domain.UserDetailsAdapter;
import ua.com.curex.service.CurrencyService;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller
public class CurrencyController {
	@Inject private CurrencyService currencyService;

	@RequestMapping(value = "/currencies", method = RequestMethod.GET)
	public String getClients(Model model){
		
		model.addAttribute("currenciesList", currencyService.getCurrencyList());
		return "currencies/currenciesList";
	}
	
	
	/*
	@RequestMapping(value = "/currencies", method = RequestMethod.GET)
	public @ResponseBody Map<String,? extends Object> getCurrencyList() throws Exception {
		Map<String,Object> modelMap = new HashMap<String,Object>(3);
		try{
            modelMap.put("currencies", currencyService.getCurrencyList());
            return modelMap;
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            return modelMap;
        }
    }
 
    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }*/
}
