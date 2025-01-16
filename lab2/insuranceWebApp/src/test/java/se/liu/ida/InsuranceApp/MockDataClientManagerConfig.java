package se.liu.ida.InsuranceApp;

import java.util.HashMap;

import org.mockito.Mockito;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import se.liu.ida.InsuranceApp.data.ClientProfile;
import se.liu.ida.InsuranceApp.services.ClientDataManagementService;

@Profile("mockClientManager")
@Configuration
public class MockDataClientManagerConfig {

	HashMap<Integer, ClientProfile> clients = new HashMap<>();

	@Bean
	@Primary
	public ClientDataManagementService clientDataManagementService() {
		ClientDataManagementService cs = Mockito.mock(ClientDataManagementService.class);

		/** Add mocked behavior */
		Mockito.when(cs.findById(Mockito.anyInt())).thenAnswer(invocation -> {
			int id = invocation.getArgument(0);
			return clients.get(id);
		});

		Mockito.when(cs.updateClientProfile(Mockito.anyInt(), Mockito.any(ClientProfile.class)))
				.thenAnswer(invocation -> {
					int id = invocation.getArgument(0);
					ClientProfile updatedProfile = invocation.getArgument(1);
					clients.replace(id, updatedProfile);
					return true;
				});

		Mockito.when(cs.addClientProfile(Mockito.any(ClientProfile.class))).thenAnswer(invocation -> {
			ClientProfile c = invocation.getArgument(0);
			clients.put(c.getClientId(), c);
			return true;
		});

		Mockito.when(cs.removeClientProfile(Mockito.anyInt())).thenAnswer(invocation -> {
			int id = invocation.getArgument(0);

			clients.remove(id);
			return true;

		});

		return cs;
	}

}
