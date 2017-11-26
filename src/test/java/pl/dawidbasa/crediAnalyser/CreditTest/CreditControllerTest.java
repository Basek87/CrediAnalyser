package pl.dawidbasa.crediAnalyser.CreditTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Application;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
/*@TestPropertySource(locations="classpath:application-test.properties")*/
/*@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert-test-data.sql")*/

public class CreditControllerTest {

	private MockMvc mockMvc;

	@Autowired
    WebApplicationContext context;

	@Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
	
	@Test
	public void shouldShowCalculatorPage() throws Exception {
	}
	
	@Test
	public void shouldShowInstalmentDetailsPage() throws Exception{
	}
}
