package pl.dawidbasa.crediAnalyser.Credit;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditController {

	@Autowired
	private CreditService creditService;

	@RequestMapping(value = "/admin/creditcalculator", method = RequestMethod.GET)
	public String showCalculatorPage(Model model) {
		List<Credit> credits = creditService.findAll();
		Credit credit = new Credit();
		model.addAttribute("credit", credit);
		model.addAttribute("credits", creditService.sortCreditsByCreditMargin(credits));
		return "/admin/calculator";
	}

	@RequestMapping(value = "/admin/creditcalculator", method = RequestMethod.POST)
	public String createNewCredit(@Valid @ModelAttribute Credit credit, BindingResult bindingResult, Model model) {

		Credit creditExist = creditService.findByMortgageName(credit.getMortgageName());
		List<Credit> credits = creditService.findAll();
		if (creditExist != null) {
			bindingResult.rejectValue("mortgageName", "error.mortgageName",
					"*There is already registered mortgage with the same Name");
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("credits", creditService.sortCreditsByCreditMargin(credits));
			return "/admin/calculator";

		} else {
			creditService.saveCredit(credit);
			model.addAttribute("successMessage", "Credit has been registered successfully");
			model.addAttribute("credits", creditService.sortCreditsByCreditMargin(credits));
			return "/admin/calculator";
		}

	}

	@RequestMapping(value = "/admin/creditcalculator/{mortgageName}", method = RequestMethod.GET)
	public String calculateInstalment(@PathVariable("mortgageName") String mortgageName, Model model) {

		Credit credit = creditService.findByMortgageName(mortgageName);
		model.addAllAttributes(creditService.calculateConstantInstalmentDetails(credit));
		model.addAllAttributes(creditService.calculateDecrasingInstalmentDetails(credit));
		model.addAttribute("calculateAllDecreasingInstalment", creditService.calculateAllDecreasingInstalments(credit));

		return "/admin/instalment";
	}

}
