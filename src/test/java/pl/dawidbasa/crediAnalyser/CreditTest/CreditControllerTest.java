package pl.dawidbasa.crediAnalyser.CreditTest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Credit.CreditController;
import pl.dawidbasa.crediAnalyser.Credit.CreditService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CreditController.class)
public class CreditControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	@MockBean
	private CreditService creditService;
	
	@Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	

}
