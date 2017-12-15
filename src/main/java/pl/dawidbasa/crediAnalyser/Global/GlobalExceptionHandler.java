package pl.dawidbasa.crediAnalyser.Global;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	    public String catchOtherExceptions(Model model,Exception ex, HttpServletRequest req ) {
		 model.addAttribute("exception", ex);
		 model.addAttribute("url", req.getRequestURL());
		return "/error";
	    }
	}
