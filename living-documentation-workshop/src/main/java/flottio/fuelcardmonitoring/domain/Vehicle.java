/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import flottio.annotations.CoreConcept;

/**
 * A vehicle with its unique Id in the fleet, and its main characteristics.
 */
@CoreConcept
public class Vehicle {
	private final int vehicleId;
	private final int tankSize;

	public Vehicle(int vehicleId, int tankSize) {
		this.vehicleId = vehicleId;
		this.tankSize = tankSize;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public int getTankSize() {
		return tankSize;
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", tankSize=" + tankSize + "L]";
	}

}
