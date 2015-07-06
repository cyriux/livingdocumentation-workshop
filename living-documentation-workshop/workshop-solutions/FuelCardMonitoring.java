/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import static flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus.ANOMALY;
import static flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus.VERIFIED;

import java.util.ArrayList;
import java.util.List;

import flottio.annotations.DomainService;
import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport.MonitoringStatus;

/**
 * Monitoring of fuel card use helps improve fuel efficiency and detect fuel
 * leakages and potential driver misbehaviors.
 */
@DomainService
public class FuelCardMonitoring {

	private final GPSTracking tracking;
	private final Geocoding geocoding;

	public FuelCardMonitoring(GPSTracking tracking, Geocoding geocoding) {
		this.tracking = tracking;
		this.geocoding = geocoding;
	}

	public FuelCardTransactionReport monitor(FuelCardTransaction transaction, Vehicle vehicle) {
		final List<String> issues = new ArrayList<String>();
		
		verifyFuelQuantity(transaction, vehicle, issues);
		verifyVehicleLocation(transaction, vehicle, issues);
		
		final MonitoringStatus status = issues.isEmpty() ? VERIFIED : ANOMALY;
		return new FuelCardTransactionReport(transaction, status, issues);
	}

	public void verifyVehicleLocation(FuelCardTransaction transaction, Vehicle vehicle, final List<String> issues) {
		final Coordinates actualLocation = tracking.locationAt(String.valueOf(vehicle.getVehicleId()),
				transaction.getDate());
		final Coordinates location = geocoding.toCoordinates(transaction.getMerchant().getAddress());
		final GeoDistance geoDistance = GeoDistance.EQUIRECTANGULAR;
		final int distance = (int) geoDistance.distanceBetween(location, actualLocation);
		if (distance >= 300) {
			issues.add("vehicle was " + distance + "m away");
		}
	}

	public void verifyFuelQuantity(FuelCardTransaction transaction, Vehicle vehicle, final List<String> issues) {
		final double fuelQuantity = transaction.getBasket().getFuelQuantity();
		if (fuelQuantity > vehicle.getTankSize()) {
			issues.add("The fuel transaction of " + ((int) fuelQuantity) + "L exceeds the tank size of "
					+ vehicle.getTankSize() + "L");
		}
	}
}
