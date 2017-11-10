package pl.dawidbasa.crediAnalyser.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration";
	}

	@RequestMapping(value = "/registration/addUser", method = RequestMethod.POST)
	public String createNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {

		// Existing users with same email are rejected
		User userExists = userService.findUserByEmail(user.getEmail());

		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		// Validation error more info about restrictions in User.java
		if (bindingResult.hasErrors()) {

			return "registration";
		}
		// Everything is ok save user.
		else {
			userService.saveUser(user);
			model.addAttribute("successMessage", "User has been registered successfully");

			return "registration";
		}
	}

}
