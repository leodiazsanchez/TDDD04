package insurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InsuranceTest {

	private insurance.InsuranceCalculator insurance;
	private insurance.Client client;

	@BeforeEach
	public void setUp() {
		insurance = new insurance.InsuranceCalculator();
		client = new Client();
	}

	@Test
	public void testException() throws InvalidClientData {
		// Negative numberOfAccients
		client.numberAccidents = -1;
		Assertions.assertThrows(InvalidClientData.class, () -> insurance.getClientDeductible(client));
	}

	@Test
	public void testInvalidAge() throws InvalidClientData {
		client.age = 0;
		Assertions.assertThrows(InvalidClientData.class, () -> insurance.getClientDeductible(client));
		client.age = 17;
		Assertions.assertThrows(InvalidClientData.class, () -> insurance.getClientDeductible(client));
		client.age = 150;
		Assertions.assertThrows(InvalidClientData.class, () -> insurance.getClientDeductible(client));
	}

	@Test
	public void testAgeDiscount() throws InvalidClientData {
		client.age = 29;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.age = 30;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.age = 31;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
	}

	@Test
	public void testLicenseDiscount() throws InvalidClientData {
		client.age = 30;
		client.yearLicense = 4;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.yearLicense = 5;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.yearLicense = 6;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
	}

	@Test
	public void testNumberOfAccidentCostNotGoldMember() throws InvalidClientData {
		client.age = 31;
		client.numberAccidents = 0;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
		client.numberAccidents = 1;
		Assertions.assertEquals(insurance.getClientDeductible(client), 6000);
		client.numberAccidents = 2;
		Assertions.assertEquals(insurance.getClientDeductible(client), 7500);
		client.numberAccidents = 3;
		Assertions.assertEquals(insurance.getClientDeductible(client), 9000);
		client.numberAccidents = 4;
		Assertions.assertEquals(insurance.getClientDeductible(client), 15000);
		client.numberAccidents = 5;
		Assertions.assertEquals(insurance.getClientDeductible(client), 15000);
	}

	@Test
	public void testNumberOfAccidentCostIsGoldMember() throws InvalidClientData {
		client.age = 31;
		client.isGoldMember = true;
		client.numberAccidents = 0;
		client.yearLicense = 1;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
		client.numberAccidents = 1;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
		client.numberAccidents = 2;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
		client.numberAccidents = 3;
		Assertions.assertEquals(insurance.getClientDeductible(client), 9000);
		client.numberAccidents = 4;
		Assertions.assertEquals(insurance.getClientDeductible(client), 15000);
		client.numberAccidents = 5;
		Assertions.assertEquals(insurance.getClientDeductible(client), 15000);
	}

	@Test
	public void testAgeDiscountPerMonth() throws InvalidClientData {
		client.yearLicense = 1;
		client.age = 29;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 600);
		client.age = 30;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 600);
		client.age = 31;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
	}
	
	@Test
	public void testLicenseDiscountPerMonth() throws InvalidClientData {
		client.age = 30;
		client.yearLicense = 4;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.yearLicense = 5;
		Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		client.yearLicense = 6;
		Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
	}
	
	@Test
	public void testRedCar() throws InvalidClientData {
		client.age = 31;
		Car car = new Car();
		car.isRed = true;
		client.cars.add(car);
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 600);
		client.cars.clear();
		client.cars.add(new Car());
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
	}
	
	@Test
	public void testMultipleCars () throws InvalidClientData {
		client.age = 31;
		client.cars.add(new Car());
		client.cars.add(new Car());
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 700);
		client.cars.add(new Car());
		client.cars.add(new Car());
		client.cars.get(3).isRed = true;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 1200);
	}
	
	@Test
	public void testMontlyDiscount () throws InvalidClientData {	
		client.age = 31;
		client.monthInsured = 12;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
		client.monthInsured = 13;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 450);
		client.yearLastAccident = 1;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
		client.yearLastAccident = 0;
		client.isGoldMember = true;
		Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 450);
	}
}
