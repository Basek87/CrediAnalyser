package pl.dawidbasa.crediAnalyser.UserTest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.dawidbasa.crediAnalyser.Application;
import pl.dawidbasa.crediAnalyser.User.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class RegistrationControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	@MockBean
	UserRepository users;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity()).build();
	}
	
	@Test
	public void testShowRegistrationPage() throws Exception{
		mockMvc.perform(get("/registration"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("user"))
		.andExpect(view().name("registration"));
	}
	
	@Test
	public void testProcessCreationUserSuccess() throws Exception{
		mockMvc.perform(post("/registration/addUser")
				.param("active", "1")
				.param("email", "test@test.pl")
				.param("password", "test1")
				.param("name", "testName")
				.param("lastName", "testLastName"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("successMessage"))
		.andExpect(view().name("registration"));
	}
}
