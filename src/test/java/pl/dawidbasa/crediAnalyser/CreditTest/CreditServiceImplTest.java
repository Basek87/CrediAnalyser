package pl.dawidbasa.crediAnalyser.CreditTest;



import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Application;
import pl.dawidbasa.crediAnalyser.Credit.Credit;
import pl.dawidbasa.crediAnalyser.Credit.CreditService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/insert-test-data.sql")
@TestPropertySource(locations = "classpath:application-test.properties")
public class CreditServiceImplTest {

	@Autowired
	DataSource datasource;;
	@Autowired
	WebApplicationContext context;
	@Autowired
	CreditService credits;

	@Test
	public void shouldFindCreditByMortgageName(){
		Credit credit = this.credits.findByMortgageName("PKO");
		assertThat(credit.getMortgageName()).startsWith("PKO");
	}
	
	@Test
	public void shouldFindAllCredits(){
		List<Credit> credits = this.credits.findAllCredits();
		assertThat(credits.size()).isEqualTo(2);
	}
	
	@Test
	public void shouldSaveCredit(){
	Credit credit = new Credit();
		credit.setId(3);
		credit.setMortgageName("SuperCredit");
		credit.setMortgageDebt(300000);
		credit.setMortgageTerm(30);
		credit.setCreditMargin(4.0);
		credit.setWibor(3.0);
		credit.setCommisionFee(5000);
	this.credits.saveCredit(credit);
	List<Credit> credits = this.credits.findAllCredits();
	assertThat(credits.size()).isEqualTo(3);
	}
	
	// PKO MARGIN 3 , NBP MAGRIN 2 list is increasing. After sorting first element should return NBP
	@Test
	public void shouldSortAllCreditsByCreditMargin(){
	List<Credit> credits = this.credits.findAllCredits();
	this.credits.sortCreditsByCreditMargin(credits);
	assertThat(credits.get(0).getMortgageName()).startsWith("NBP");
	
	}
	
	//ComissionFee 5000
	//CreditMargin 3
	//MortgageDebt 300000
	//WIBOR 2
	//MortgageTerm 30
	@Test
	public void instalmentShouldReturnExpectedValue(){
		Credit credit = this.credits.findByMortgageName("PKO");
		List<BigDecimal> decrasingInstalments = this.credits.calculateAllDecreasingInstalments(credit);
		assertThat(decrasingInstalments.size()).isEqualTo(360);
		// Test First Installment
		assertThat(decrasingInstalments.get(0).setScale(2, RoundingMode.HALF_EVEN)).isEqualTo("2118.06");
		// Test Last Installment
		assertThat(decrasingInstalments.get(359).setScale(2, RoundingMode.HALF_EVEN)).isEqualTo("850.75");
	}
	
	@Test
	public void calculateDecrasingInstalmentShouldReturnExpectedValue(){
		Credit credit = this.credits.findByMortgageName("PKO");
		Map<String, BigDecimal> map =new HashMap<>(); 
		map = this.credits.calculateDecrasingInstalmentDetails(credit);
	
		assertThat(map).contains(entry
				("decrasingInstalmentTotalCost",BigDecimal.valueOf(534385.42).setScale(2)));
		assertThat(map).contains(entry
				("decrasingInstalmentAverage",BigDecimal.valueOf(1484.40).setScale(2)));
		assertThat(map).contains(entry
				("decrasingInstalmentMin",BigDecimal.valueOf(850.75).setScale(2)));
		assertThat(map).contains(entry
				("decrasingInstalmentMax",BigDecimal.valueOf(2118.06).setScale(2)));
	}
	
	@Test
	public void calculateConstantInstalmentShouldReturnExpectedValue(){
		Credit credit = this.credits.findByMortgageName("PKO");
		Map<String, BigDecimal> map =new HashMap<>(); 
		map = this.credits.calculateConstantInstalmentDetails(credit);
		
		assertThat(map).contains(entry
				("constantInstalment",BigDecimal.valueOf(1637.31).setScale(2)));
		assertThat(map).contains(entry
				("constantInstalmentTotalCost",BigDecimal.valueOf(589430.17).setScale(2)));
	}
}
