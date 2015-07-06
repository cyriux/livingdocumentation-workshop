/**
 * CREATED ON April 12, 2015
 */
package flottio.fuelcardmonitoring.domain;

import static flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus.ANOMALY;
import static flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus.VERIFIED;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import flottio.annotations.DomainService;
import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus;

/**
 * Monitoring of fuel card use helps improve fuel efficiency and detect fuel
 * leakages and potential driver misbehaviors.
 */
@DomainService
public class FuelCardMonitoring {

	// The LocationTrackingService instance
	private final GPSTracking tracking;

	// The geo-coding instance
	private final Geocoding geocoding;

	// default constructor
	public FuelCardMonitoring(GPSTracking tracking, Geocoding geocoding) {
		this.tracking = tracking;
		this.geocoding = geocoding;
	}

	// This public method does the job
	public FuelCardTransactionReport monitor(FuelCardTransaction transaction, Vehicle vehicle) {
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
}
