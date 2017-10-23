package pl.dawidbasa.crediAnalyser.Credit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("creditService")
public class CreditServiceImpl implements CreditService {

	@Autowired
	CreditRepository creditRepository;

	public void saveCredit(Credit credit) {
		creditRepository.save(credit);
	}

	public List<Credit> findAll() {
		return creditRepository.findAll();
	}

	public Credit findByMortgageName(String mortgageName) {
		return creditRepository.findByMortgageName(mortgageName);
	}

	public List<Credit> sortCreditsByCreditMargin(List<Credit> credits) {
		credits.sort(Comparator.comparingDouble(Credit::getCreditMargin));
		return credits;
	}

	public List<Double> calculateAllDecreasingInstalments(Credit credit) {
		final int mortgageTermMonths = credit.getMortgageTerm() * 12;
		final double monthlyIntrest = ((credit.getWibor() + credit.getCreditMargin()) / 100) / 12;
		final double capitalElement = (credit.getMortgageDebt() + credit.getCommisionFee()) / (mortgageTermMonths);

		List<Double> installmentsList = new ArrayList<>();

		for (int i = 0; i < mortgageTermMonths;) {
			double interestElement = (credit.getMortgageDebt() - (capitalElement * i)) * (monthlyIntrest);
			double installment = capitalElement + interestElement;
			installmentsList.add(installment);
			i++;
		}
		return installmentsList;
	}

	public Map<String, Double> calculateDecrasingInstalmentDetails(Credit credit) {

		List<Double> lista = calculateAllDecreasingInstalments(credit);
		Map<String, Double> map = new HashMap<>();
		map.put("decrasingInstalmentTotalCost", lista.stream().mapToDouble(Double::valueOf).sum());
		map.put("decrasingInstalmentAverage", lista.stream().mapToDouble(Double::valueOf).average().getAsDouble());
		map.put("decrasingInstalmentMin", lista.stream().mapToDouble(Double::valueOf).min().getAsDouble());
		map.put("decrasingInstalmentMax", lista.stream().mapToDouble(Double::valueOf).max().getAsDouble());
		return map;

	}

	public Map<String, Double> calculateConstantInstalmentDetails(Credit credit) {

		Map<String, Double> map = new HashMap<>();

		final int mortgageTermMonths = credit.getMortgageTerm() * 12;
		final double annualInterest = credit.getWibor() + credit.getCreditMargin();
		final double monthlyIntrest = (annualInterest / 12) / 100;
		final double q = 1 + monthlyIntrest;
		final double qn = Math.pow(q, mortgageTermMonths);
		final double instalment = (credit.getMortgageDebt() + credit.getCommisionFee()) * qn * ((q - 1) / (qn - 1));

		map.put("constantInstalment", instalment);
		map.put("constantInstalmentTotalCost", instalment * mortgageTermMonths);

		return map;
	}

}
