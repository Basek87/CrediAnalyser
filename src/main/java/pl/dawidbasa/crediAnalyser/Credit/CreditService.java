package pl.dawidbasa.crediAnalyser.Credit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CreditService {

	Credit findByMortgageName(String mortgageName);
	public void saveCredit(Credit credit);
	List<Credit> findAll();
	public List<Credit> sortCreditsByCreditMargin(List<Credit> credits);
	public List<BigDecimal> calculateAllDecreasingInstalments(Credit credit);
	public Map<String, BigDecimal> calculateDecrasingInstalmentDetails(Credit credit);
	public Map<String, BigDecimal> calculateConstantInstalmentDetails(Credit credit);
	
}
