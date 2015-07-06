package flottio.fuelcardmonitoring.domain;

import static flottio.fuelcardmonitoring.domain.DistanceUnit.KM;
import static flottio.fuelcardmonitoring.domain.VolumeUnit.LITER;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import flottio.fuelcardmonitoring.domain.DistanceUnit;
import flottio.fuelcardmonitoring.domain.FuelEconomy;
import flottio.fuelcardmonitoring.domain.VolumeUnit;

public class FuelManagementTest {

	@Test
	public void should_return_UNKNOWN_on_first_reading() {
		assertEquals(FuelEconomy.UNKNOWN, fuelEconomy(63, LITER, 10630, KM, 10630));
	}

	@Test
	public void should_return_10KMperLiter_on_second_reading() {
		assertEquals(new FuelEconomy(660, 66), fuelEconomy(66, LITER, 10630 + 660, KM, 10630));
	}

	private FuelEconomy fuelEconomy(int volume, VolumeUnit liter, long odometer, DistanceUnit km, long previousOdometer) {
		if (odometer == previousOdometer) {
			return FuelEconomy.UNKNOWN;
		}
		return new FuelEconomy(odometer - previousOdometer, volume);
	}
}
