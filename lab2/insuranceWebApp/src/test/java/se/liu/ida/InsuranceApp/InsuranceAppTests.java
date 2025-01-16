package se.liu.ida.InsuranceApp;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import se.liu.ida.InsuranceApp.services.ClientDataManagementService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles() // we can choose between different test configurations for different layers
@AutoConfigureMockMvc
public class InsuranceAppTests {

	/**
	 * We are going to mock the mvc layer to simplify the setup
	 */
	@Autowired
	private MockMvc mvc;

	@Autowired
	private InsuranceController ic;

	@Autowired
	private ClientDataManagementService cm;

	@BeforeEach
	public void setup() {
		this.mvc = MockMvcBuilders.standaloneSetup(ic).build();
	}

	@Test
	public void test_getClientNumber() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getClientNumber"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.equalTo("0")));
		
		mvc.perform(MockMvcRequestBuilders.get("/addNewClient"))
			.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/getClientNumber"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.equalTo("1")));

	}

	@Test
	public void test_addNewClient() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/addNewClient")).andExpect(status().isOk());
	}

	@Test
	public void test_getClientInfo() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/addNewClient")).andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders.get("/getClientData").param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.clientId").value(1))
			.andExpect(jsonPath("$.firstName").value("Smith")) // IC sends params in the wrong order...
			.andExpect(jsonPath("$.lastName").value("Joan"))
			.andExpect(jsonPath("$.yearOfBirth").value(1990))
			.andExpect(jsonPath("$.yearOfLicence").value(2013));
	}

	@Test
	public void test_getDeductible() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/addNewClient")).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/getClientDeductible")
			.param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.equalTo("5000"))); // Mocked response
	}

	@Test
	public void test_getMonthlyRate() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders.get("/addNewClient")).andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.get("/getClientMonthlyRate").param("id", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.equalTo("500"))); // Mocked response
	}

	@Test
	public void test_addCar() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/addNewCar")
			.param("clientId", "1")
			.param("color", "black")
			.param("year", "2009"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.equalTo("Added new car to client" + 1)));
	}
}
