package pl.dawidbasa.crediAnalyser.Login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.dawidbasa.crediAnalyser.User.User;
import pl.dawidbasa.crediAnalyser.User.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {

		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.saveUser(user);
			model.addAttribute("successMessage", "User has been registered successfully");
			return "registration";
		}
	}

	@RequestMapping(value = "/admin/home", method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		model.addAttribute("userName","Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");
		return "admin/home";
	}

}
