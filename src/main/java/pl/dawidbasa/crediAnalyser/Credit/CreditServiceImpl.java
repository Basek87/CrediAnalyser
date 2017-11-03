package pl.dawidbasa.crediAnalyser.Credit;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

	public List<BigDecimal> calculateAllDecreasingInstalments(Credit credit) {
		
		// Mortgage term in months =  mortgage term * 12 
		final int mortgageTermMonths = credit.getMortgageTerm() * 12;
		
		// Monthly intrest rate = (WIBOR + Credit margin) / 1200   
		// (12)*(100) 12 - months, 100 - conversion to percentages.  
		final BigDecimal monthlyIntrest = BigDecimal.valueOf(credit.getWibor())
				.add(BigDecimal.valueOf(credit.getCreditMargin()))
				.divide(BigDecimal.valueOf(1200),10,RoundingMode.HALF_UP);
		
		// Mortgage Capital = Mortgage debt + Comission fee
		final BigDecimal capitalElement = BigDecimal.valueOf(credit.getMortgageDebt())
				.add(BigDecimal.valueOf(credit.getCommisionFee()));
		
		// Create Variable and set to 0 , this variable store currently paid capital.
		BigDecimal repaidedCapitalElement = new BigDecimal(0);
		
		// Create list of installments.
		List<BigDecimal> installmentsList = new ArrayList<>();
		
		// Calculate all instalments 
		for (int i = 0; i < mortgageTermMonths;) {
			
			// Intrest element = (Capital element - Repaided Capital Element) * Monthly intrest.
			// Precision of 4 decimal places, round up.
			BigDecimal interestElement = capitalElement
					.subtract(repaidedCapitalElement)
					.multiply(monthlyIntrest).setScale(4, RoundingMode.HALF_UP);
			
			// Calculate single instalment 
			// installment = (Capital element / Number of instalments) + intrest element
			BigDecimal installment = capitalElement
					.divide(BigDecimal.valueOf(mortgageTermMonths),4,RoundingMode.HALF_UP)
					.add(interestElement);
			
			// Currently paid capital in every iteration add vale to pocket.
			repaidedCapitalElement = capitalElement
					.divide(BigDecimal.valueOf(mortgageTermMonths),4,RoundingMode.HALF_UP)
					.add(repaidedCapitalElement);
			
			// Add installment to List
			installmentsList.add(installment);
			i++;
		}
		return installmentsList;
	}
	
	// Calculate Min, Max, Average and sum of all installments stored in BigDecimal value.
	// String Represents name of the atribute passed to Model in controller layer then to the view.
	public Map<String, BigDecimal> calculateDecrasingInstalmentDetails(Credit credit) {

		// Get all installments
		List<BigDecimal> lista = calculateAllDecreasingInstalments(credit);
		
		Map<String, BigDecimal> map = new HashMap<>();
		
		// Calculate Sum of all installments
		map.put("decrasingInstalmentTotalCost", lista.stream()
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(2, RoundingMode.HALF_UP));
		
		// Conversion to double , lose some preciosion but here is not needed, only for preview. 
		// Calculate average from double method.
		map.put("decrasingInstalmentAverage", BigDecimal.valueOf(lista.stream()
				.mapToDouble(BigDecimal::doubleValue)
				.average().getAsDouble())
				.setScale(2, RoundingMode.HALF_UP));
		
		// Conversion to double , lose some preciosion but here is not needed, only for preview. 
		// Calculate minimal instalment from double method.
		map.put("decrasingInstalmentMin",BigDecimal.valueOf(lista.stream()
				.mapToDouble(BigDecimal::doubleValue)
				.min()
				.getAsDouble())
				.setScale(2, RoundingMode.HALF_UP));
		
		// Conversion to double , lose some preciosion but here is not needed, only for preview. 
		// Calculate maximal instalment from double method.
		map.put("decrasingInstalmentMax",BigDecimal.valueOf(lista.stream()
				.mapToDouble(BigDecimal::doubleValue)
				.max().getAsDouble())
				.setScale(2, RoundingMode.HALF_UP));
		
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
