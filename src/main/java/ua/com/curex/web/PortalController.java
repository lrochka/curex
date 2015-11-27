/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
@Controller
public class PortalController {
	
	// Use this instead of <mvc:view-controller> so we can handle all HTTP methods and not just GET. (Forwarding to the
	// access denied page preserves the HTTP request method.)
	@RequestMapping(value = "/accessdenied.html")
	public String getAccessDenied() { return "accessdenied"; }
}
