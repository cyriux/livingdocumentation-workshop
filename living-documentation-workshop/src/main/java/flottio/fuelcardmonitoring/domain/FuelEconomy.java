package flottio.fuelcardmonitoring.domain;

import static java.lang.Math.abs;
import flottio.annotations.ValueObject;
import flottio.fuelcardmonitoring.infra.WebServiceGPSTrackingAdapter;

/**
 * The fuel economy as the relation between the volume of gas consumed and the
 * corresponding distance traveled.
 */
@ValueObject
public class FuelEconomy {
	public static final FuelEconomy UNKNOWN = new FuelEconomy(0);

	private final double economy;
	private WebServiceGPSTrackingAdapter gpsTrackingAdapter;

	public FuelEconomy(double distanceKmByLiter) {
		this.economy = distanceKmByLiter;
	}

	public FuelEconomy(double distanceKm, double volumeLiter) {
		this(distanceKm / volumeLiter);
	}

	public double inKilometerPerLiter() {
		return economy;
	}

	public double inLiterPer100Kilometers() {
		return 100. / economy;
	}

	@Override
	public int hashCode() {
		return (int) Double.doubleToLongBits(economy) >>> 32;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof FuelEconomy)) {
			return false;
		}
		FuelEconomy other = (FuelEconomy) obj;
		return abs(economy - other.economy) < 0.001;
	}

	@Override
	public String toString() {
		if (this == UNKNOWN) {
			return "UNKNOWN";
		}
		return "FuelEconomy [economy=" + economy + "]";
	}

}