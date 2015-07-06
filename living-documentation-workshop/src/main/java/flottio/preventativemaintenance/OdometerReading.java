/**
 * 
 */
package flottio.preventativemaintenance;

import java.util.Date;

import flottio.annotations.ValueObject;
import flottio.fuelcardmonitoring.domain.DistanceUnit;

/**
 * A reading of the odometer at some date and time. It's typically reported by
 * the driver for each refueling, but may be automated as well.
 */
@ValueObject
public class OdometerReading {

	private final Date date;
	private final DistanceUnit unit;
	private final Long odometer;

	public OdometerReading(Date date, DistanceUnit unit, Long odometer) {
		this.date = date;
		this.unit = unit;
		this.odometer = odometer;
	}

	public Date getDate() {
		return date;
	}

	public DistanceUnit getUnit() {
		return unit;
	}

	public Long getOdometer() {
		return odometer;
	}

	@Override
	public int hashCode() {
		return date.hashCode() ^ odometer.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OdometerReading)) {
			return false;
		}
		final OdometerReading other = (OdometerReading) obj;
		return other.date.equals(other.date) && odometer.equals(other.odometer);
	}

	@Override
	public String toString() {
		return "OdometerReading [date=" + date + ", odometer=" + odometer + " (" + unit + ")]";
	}
}
