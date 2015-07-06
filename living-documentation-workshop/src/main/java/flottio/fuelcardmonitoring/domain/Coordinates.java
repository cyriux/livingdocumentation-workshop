/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import flottio.annotations.ValueObject;


/**
 * A global location expressed in latitude and longitude
 */
@ValueObject
public class Coordinates {

	private final double latitude;
	private final double longitude;

	public Coordinates(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		return (int) (latitude - longitude) >>> 32;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Coordinates)) {
			return false;
		}
		final Coordinates other = (Coordinates) obj;
		return latitude == other.latitude && longitude == other.longitude;
	}

	@Override
	public String toString() {
		return "(" + latitude + ", " + longitude + "])";
	}

}
