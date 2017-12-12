package pl.dawidbasa.crediAnalyser.CreditTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Application;
import pl.dawidbasa.crediAnalyser.Configuration.SecurityTestConfiguration;
import pl.dawidbasa.crediAnalyser.User.MyUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class, SecurityTestConfiguration.class })
@TestPropertySource(locations="classpath:application-test.properties")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/insert-test-data.sql")

public class MyTest {

	@Autowired
	DataSource datasource;
	
	@Autowired
	public MyUserDetailsService userDetailsService;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(roles="ADMIN")
	public void requestProtectedUrlWithUser() throws Exception {
 
	mvc
		 .perform(get("/admin/creditcalculator"))
		 .andExpect(status()
				 .isOk())
		 .andExpect(view().name(""));
         // Ensure it appears we are authenticated with user
        
	}

}
