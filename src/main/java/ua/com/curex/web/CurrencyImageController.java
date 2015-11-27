package ua.com.curex.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.curex.dao.CurrencyDao;
import ua.com.curex.domain.Currency;
import ua.com.curex.service.CurrencyService;

@Controller
@RequestMapping("/currencies")
public class CurrencyImageController{
	
	@Inject private CurrencyService currencyService;

	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void showImage(@RequestParam("alpha") String alpha, HttpServletResponse response, HttpServletRequest request) 
	          throws ServletException, IOException{
	    Currency curr = currencyService.getCurrencyByAlpha(alpha);        
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(curr.getImg());
	    response.getOutputStream().close();
	}
	//private static final long serialVersionUID=1L;
	
	//@Inject private CurrencyService currencyService;
	
	/*protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String alpha = req.getParameter("alpha");
		Currency curr= currencyService.getCurrencyByAlpha(alpha);
		byte[] image = curr.getImg();
		res.setContentType("image/jpeg");
		ServletOutputStream outputStream = res.getOutputStream();
		outputStream.write(image);
		outputStream.close();
	}*/

}
