package pl.dawidbasa.crediAnalyser.CreditTest;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Application;
import pl.dawidbasa.crediAnalyser.Configuration.SecurityTestConfiguration;
import pl.dawidbasa.crediAnalyser.Credit.Credit;
import pl.dawidbasa.crediAnalyser.Credit.CreditRepository;
import pl.dawidbasa.crediAnalyser.User.MyUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { Application.class, SecurityTestConfiguration.class })
/*
 * @TestPropertySource(locations = "classpath:application-test.properties")
 * 
 * @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts =
 * "classpath:/insert-test-data.sql")
 */
public class CreditControllerTest {

	private MockMvc mockMvc;
	private Credit credit;

	@Autowired
	WebApplicationContext context;
	DataSource datasource;
	MyUserDetailsService userDetailsService;

	@MockBean
	CreditRepository credits;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

		credit = new Credit();
		credit.setId(1);
		credit.setMortgageName("SuperCredit");
		credit.setMortgageDebt(300000);
		credit.setMortgageTerm(30);
		credit.setCreditMargin(4.0);
		credit.setWibor(3.0);
		credit.setCommisionFee(5000);
		when(this.credits.findByMortgageName("SuperCredit")).thenReturn(credit);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testShowCalculatorPage() throws Exception {
		mockMvc.perform(get("/admin/creditcalculator")).andExpect(status().isOk())
				.andExpect(model().attributeExists("credit")).andExpect(model().attributeExists("credits"))
				.andExpect(view().name("/admin/calculator"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testProcessCreationCreditSuccess() throws Exception {
		mockMvc.perform(post("/admin/creditcalculator/addCredit").param("mortgageName", "PKO")
				.param("mortgageDebt", "100000").param("mortgageTerm", "30").param("creditMargin", "3")
				.param("wibor", "2").param("commisionFee", "5000")).andExpect(status().isOk())
				.andExpect(model().attributeExists("successMessage")).andExpect(model().attributeExists("credits"))
				.andExpect(view().name("/admin/calculator"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/admin/creditcalculator/addCredit").param("mortgageName", "PKO")
				.param("mortgageDebt", "100000").param("mortgageTerm", "30")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("credit"))
				.andExpect(model().attributeHasFieldErrors("credit", "creditMargin"))
				.andExpect(model().attributeHasFieldErrors("credit", "wibor"))
				.andExpect(model().attributeHasFieldErrors("credit", "commisionFee"))
				.andExpect(view().name("/admin/calculator"));
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void testProcessFindFormByMortgageName() throws Exception {

		mockMvc.perform(get("/admin/creditcalculator/{mortgageName}", credit.getMortgageName()))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("decrasingInstalmentTotalCost"))
		.andExpect(model().attributeExists("decrasingInstalmentAverage"))
		.andExpect(model().attributeExists("decrasingInstalmentMin"))
		.andExpect(model().attributeExists("decrasingInstalmentMax"))
		.andExpect(model().attributeExists("calculateAllDecreasingInstalment"))
		.andExpect(model().attributeExists("constantInstalment"))
		.andExpect(model().attributeExists("constantInstalmentTotalCost"))
		
		.andExpect(view().name("/admin/instalment"));
	}

}
