package pl.dawidbasa.crediAnalyser.CreditTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
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

	private MockMvc mvc;
	
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
	
	// PKO MARGIN 3 , NBP MAGRIN 2 list is increasing. First element should return NBP
	@Test
	public void shouldSortAllCreditsByCreditMargin(){
	List<Credit> credits = this.credits.findAllCredits();
	this.credits.sortCreditsByCreditMargin(credits);
	assertThat(credits.get(0).getMortgageName()).startsWith("NBP");
	
	}
	
}
