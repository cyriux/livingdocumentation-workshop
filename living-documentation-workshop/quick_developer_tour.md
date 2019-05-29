## 1. [FuelCardTxListener](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/infra/FuelCardTxListener.java#L18)

*The MQ listener which triggers a full chain of processing*

> Listens to incoming fuel card transactions from the external system of the
> Fuel Card Provider





## 2. [FuelCardTransaction](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/domain/FuelCardTransaction.java#L13)

*The incoming fuel card transaction*

> A transaction between a card and a merchant as reported by the fuel card
> provider





## 3. [FuelCardMonitoring](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/domain/FuelCardMonitoring.java#L22)

*The service which takes care of all the fuel card monitoring*

> Monitoring of fuel card use to help improve fuel efficiency and detect fuel
> leakages and potential driver misbehaviors.





## 4. [monitor(transaction, vehicle)](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/domain/FuelCardMonitoring.java#L40)

*The method which does all the potential fraud detection for an incoming fuel card transaction*


```
@flottio.annotations.GuidedTour(name="Quick Developer Tour",
	description="The method which does all the potential fraud detection for an incoming fuel card transaction",
	rank=4)
public flottio.fuelcardmonitoring.domain.FuelCardTransactionReport monitor(flottio.fuelcardmonitoring.domain.FuelCardTransaction transaction, flottio.fuelcardmonitoring.domain.Vehicle vehicle) {

		final Coordinates actualLocation = tracking.locationAt(String.valueOf(vehicle.getVehicleId()),
				transaction.getDate());
		final Coordinates location = geocoding.toCoordinates(transaction.getMerchant().getAddress());
		final GeoDistance geoDistance = GeoDistance.EQUIRECTANGULAR;
		final int distance = (int) geoDistance.distanceBetween(location, actualLocation);

		// the list of issues detected
		final List<String> list = new ArrayList<String>();

		final double fuelQuantity = transaction.getBasket().getFuelQuantity();
		final int capacity = vehicle.getTankSize();

		String message = null;
		// if the fuel quantity of the transaction exceeds the tank size
		if (fuelQuantity > capacity) {
			message = "The fuel transaction of " + ((int) fuelQuantity) + "L exceeds the tank size of " + capacity
					+ "L";
		}
		list.add(message);

		if (distance >= 300) {
			list.add("vehicle was " + distance + "m away");
		}

		// don't forget to clean up possible null in the list
		List<String> l = new ArrayList<String>();
		l.add(null);
		Iterator<String> it = l.iterator();
		while (it.hasNext()) {
			if (it.next() == null) {
				it.remove();
			}
		}
		// if the list of issues is empty the status is VERIFIED, otherwise it's
		// ANOMALY
		MonitoringStatus status = null;
		if (list.size() == 0) {
			status = VERIFIED;
		} else {
			status = ANOMALY;
		}
		return new FuelCardTransactionReport(transaction, status, list);
	}

```





## 5. [FuelCardTransactionReport](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/domain/FuelCardTransactionReport.java#L17)

*The report for an incoming fuel card transaction*

> The fuel card monitoring report for one transaction, with a status and any
> potential issue found.





## 6. [ReportDAO](https://github.com/cyriux/livingdocumentation-workshop/blob/master/living-documentation-workshop/src/main/java/flottio/fuelcardmonitoring/infra/ReportDAO.java#L12)

*The DAO to store the resulting fuel card reports after processing*

