package pl.dawidbasa.crediAnalyser.Credit;

import java.util.List;
import java.util.Map;

public interface CreditService {

	Credit findByMortgageName(String mortgageName);
	public void saveCredit(Credit credit);
	List<Credit> findAll();
	public List<Credit> sortCreditsByCreditMargin(List<Credit> credits);
	public List<Double> calculateAllDecreasingInstalments(Credit credit);
	public Map<String, Double> calculateDecrasingInstalmentDetails(Credit credit);
	public Map<String, Double> calculateConstantInstalmentDetails(Credit credit);
	
}
