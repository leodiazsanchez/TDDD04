package se.liu.ida.InsuranceApp.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.liu.ida.InsuranceApp.data.CarData;
import se.liu.ida.InsuranceApp.data.ClientProfile;

@Service
public class InsuranceServiceImpl implements InsuranceService {

	@Autowired
	private ClientDataManagementService clientDB;
	private int counter;

	public InsuranceServiceImpl(ClientDataManagementService clientDB) {
		this.clientDB = clientDB;
		counter = 0;
	}

	@Override
	public Integer clientNumber() {
		return counter;
	}

	@Override
	public Boolean isClientGoldMember(int clientId) {
		ClientProfile cl = getClientProfile(clientId);
		return cl.getGoldMember();
	}

	@Override
	public Boolean registerNewMember(String lastName, String firstName, int yearOfBirth, int yearOfLicence) {
		ClientProfile new_c = new ClientProfile(counter++, lastName, firstName, yearOfBirth, yearOfLicence);
		clientDB.addClientProfile(new_c);
		return true;
	}

	@Override
	public int registerNewAccident(int clientId) {
		ClientProfile c = getClientProfile(clientId);
		c.setNumberOfAccidentsThisYear(c.getNumberOfAccidentsThisYear() + 1);
		return getClientDeductible(clientId);
	}

	@Override
	public Boolean updateDatabase() {
		for (int i = 0; i < counter; i++) {
			ClientProfile c = getClientProfile(counter);
			;
			if (c.getYearsWithoutAccidents() >= 5) {
				c.setGoldMember(true);
			}
			c.setNumberOfAccidentsThisYear(0);
		}
		return true;
	}

	@Override
	public int getClientDeductible(int clientId) {

		ClientProfile c = getClientProfile(clientId);

		int yearToday = LocalDate.now().getYear();

		int cost = (yearToday - c.getYearOfBirth() > 30 || yearToday - c.getYearOfLicence() > 5) ? 5000 : 8000;

		if (c.getNumberOfAccidentsThisYear() == 1 && !c.getGoldMember()) {
			cost += 1000;
		} else if (c.getNumberOfAccidentsThisYear() == 2 && !c.getGoldMember()) {
			cost += 2500;
		} else if (c.getNumberOfAccidentsThisYear() == 3) {
			cost += 4000;
		} else if (c.getNumberOfAccidentsThisYear() >= 4) {
			cost += 10000;
		}

		return cost;
	}

	@Override
	public int MonthlyInsuranceCost(int clientId) {

		ClientProfile c = getClientProfile(clientId);

		int yearToday = LocalDate.now().getYear();
		
		int cost = (yearToday - c.getYearOfBirth() > 30 || yearToday - c.getYearOfLicence() > 5) ? 500 : 600;

		boolean firstCar = true;
		for (CarData car : c.getCarsInsured()) {
			if (firstCar) {
				if (car.getCarColor() == "red") {
					cost += 100;
				}
				firstCar = false;
				continue;
			}

			cost += car.getCarColor() == "red" ? 300 : 200;
		}

		if (yearToday - c.getYearBecameClient() > 1 && (c.getNumberOfAccidentsThisYear() == 0 || c.getGoldMember())) {
			cost *= 0.9;
		}

		return cost;
	}

	@Override
	public Boolean addNewCarToMember(int id, String color, int year) {
		ClientProfile c = getClientProfile(id);
		c.addCarInsured(new CarData(color, year));
		return true;
	}

	@Override
	public ClientProfile getClientProfile(int id) {
		return clientDB.findById(id);
	}

}
