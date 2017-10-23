package pl.dawidbasa.crediAnalyser.Credit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditController {

	@Autowired
	private CreditService creditService;

	@RequestMapping(value = "/admin/creditcalculator", method = RequestMethod.GET)
	public String showCalculatorPage(Model model, Credit credit) {
		List<Credit> credits = creditService.findAll();
		model.addAttribute("credit", credit);
		model.addAttribute("credits", creditService.sortCreditsByCreditMargin(credits));
		return "/admin/calculator";
	}

	@RequestMapping(value = "/admin/creditcalculator", method = RequestMethod.POST)
	public String createNewCredit(@ModelAttribute Credit credit, Model model) {
		creditService.saveCredit(credit); 
		return "redirect:/admin/creditcalculator";
	}

	@RequestMapping(value = "/admin/creditcalculator/{mortgageName}", method = RequestMethod.GET)
	public String calculateInstalment(@PathVariable("mortgageName") String mortgageName, Model model) {
		
		Credit credit = creditService.findByMortgageName(mortgageName); 
		model.addAllAttributes(creditService.calculateConstantInstalmentDetails(credit));
		model.addAllAttributes(creditService.calculateDecrasingInstalmentDetails(credit));
		model.addAttribute("countAllDecreasingInstalment", creditService.calculateAllDecreasingInstalments(credit));
		return "/admin/instalment";
	}
	
}

