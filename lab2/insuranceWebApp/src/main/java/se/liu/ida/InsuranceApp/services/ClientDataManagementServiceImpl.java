package se.liu.ida.InsuranceApp.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import se.liu.ida.InsuranceApp.data.ClientProfile;

@Service
public class ClientDataManagementServiceImpl implements ClientDataManagementService {
	
	HashMap<Integer,ClientProfile> db = new HashMap<>();

	@Override
	public ClientProfile findById(int id) {
		return db.get(id);
	}

	@Override
	public Boolean updateClientProfile(int id, ClientProfile c) {
		db.replace(id, c);
		return true;
	}

	@Override
	public Boolean addClientProfile(ClientProfile c) {
		db.put(c.getClientId(), c);
		return true;
	}

	@Override
	public Boolean removeClientProfile(int id) {
		db.remove(id);
		return true;
	}

}
