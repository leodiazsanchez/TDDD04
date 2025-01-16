package insurance;

public class InsuranceCalculator {
	/**
	 * Calculate the deductible for the client
	 * 
	 * Base cost is 5000 SEK if the client is above 30 or has had a driving license
	 * for more that 5 years and 8000 SEK otherwise
	 * 
	 * With every accident for that calendar the deductible increases: 1 accident :
	 * by 1000 SEK 2 accidents : by 2500 SEK 3 accidents : by 4000 SEK 4 accidents
	 * and more by : 10000 SEK
	 * 
	 * If the client if a gold member, then for the first 2 accidents, there is no
	 * increase but for 3 accidents and more normal rates apply
	 * 
	 * @param clientId
	 * @return the amount of the deductible
	 * @throws InvalidClientData
	 */
	int getClientDeductible(Client cl) throws InvalidClientData {

		if (0 > cl.numberAccidents || cl.age < 18 || cl.age > 149)
			throw new InvalidClientData();

		int cost = (cl.age > 30 || cl.yearLicense > 5) ? 5000 : 8000;

		if (cl.numberAccidents == 1 && !cl.isGoldMember) {
			cost += 1000;
		} else if (cl.numberAccidents == 2 && !cl.isGoldMember) {
			cost += 2500;
		} else if (cl.numberAccidents == 3) {
			cost += 4000;
		} else if (cl.numberAccidents >= 4) {
			cost += 10000;
		}

		return cost;
	}

	/**
	 * Calculate the monthly cost for the service for the client First year rate is
	 * 500SEK if the client is above 30 or has had a driving license for more that 5
	 * years and 600 SEK otherwise If the car is red the cost goes up by 100SEK
	 * 
	 * Each additional car adds 200 SEK unless it is red then it adds 300SEK After
	 * the first year, there is a 10% discount if there were 0 accidents that year
	 * or if the client is a gold member
	 * 
	 * @param clientId
	 * @return
	 */
	int MonthlyInsuranceCost(Client cl) {

		int cost = (cl.age > 30 || cl.yearLicense > 5) ? 500 : 600;

		boolean firstCar = true;
		for (Car car : cl.cars) {
		    if (firstCar) {
		        if (car.isRed) {
		            cost += 100;
		        }
		        firstCar = false;
		        continue;
		    }
		    
		    cost += car.isRed ? 300 : 200;
		}

		if (cl.monthInsured > 12 && (cl.yearLastAccident == 0 || cl.isGoldMember)) {
			cost *= 0.9;
		}

		return cost;
	}
}
