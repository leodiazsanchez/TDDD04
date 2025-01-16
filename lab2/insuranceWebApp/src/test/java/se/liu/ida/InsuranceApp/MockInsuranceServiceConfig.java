package se.liu.ida.InsuranceApp;



import java.util.ArrayList;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import se.liu.ida.InsuranceApp.data.ClientProfile;
import se.liu.ida.InsuranceApp.services.InsuranceService;

@Profile("mockInsuranceService")
@Configuration
public class MockInsuranceServiceConfig {
	
	@Bean
	@Primary
	public InsuranceService insuranceService() {
		InsuranceService is =  Mockito.mock(InsuranceService.class);
			
		ArrayList <ClientProfile> clients = new ArrayList<>();
	    
		/** Add mocked behavior */
		Mockito.when(is.clientNumber()).thenAnswer(invocation -> clients.size());

		 
		 Mockito.when(is.registerNewMember(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
         .thenAnswer(invocation -> {
            clients.add(new ClientProfile(clients.size(),
                 invocation.getArgument(0), 
                 invocation.getArgument(1),
                 invocation.getArgument(2), 
                 invocation.getArgument(3)));
             return true;
         });
		 
		 Mockito.when(is.getClientDeductible(Mockito.anyInt())).thenReturn(5000);
		 
		 Mockito.when(is.MonthlyInsuranceCost(Mockito.anyInt())).thenReturn(500);
		 
		 Mockito.when(is.getClientProfile(Mockito.anyInt())).thenAnswer(invocation -> {
			    int id = invocation.getArgument(0);  
			    return clients.get(id); 
			});
		 
	 
		 return is;
	}

}
