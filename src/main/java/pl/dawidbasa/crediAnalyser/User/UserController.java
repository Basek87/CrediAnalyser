package pl.dawidbasa.crediAnalyser.User;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("ROLE_USER")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/myhome")
	public String showHomePage(Model model){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user =userService.findUserByEmail(auth.getName());
		model.addAttribute("name", user.getName());
		model.addAttribute("lastName", user.getLastName());
		model.addAttribute("datetime", new Date());
		return "index";
		
	}

}
