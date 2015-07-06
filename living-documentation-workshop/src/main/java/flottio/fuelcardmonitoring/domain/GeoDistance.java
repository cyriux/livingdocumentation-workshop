/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * The calculation of the geographical distance between two points expressed in
 * (latitude, longitude).
 */
public enum GeoDistance {

	/** The Haversine calculation with the equirectanguar approximation */
	EQUIRECTANGULAR;

	protected final double R = 6371000; // metres

	public double distanceBetween(Coordinates p1, Coordinates p2) {
		final double t1 = toRadians(p1.getLatitude());
		final double t2 = toRadians(p2.getLatitude());
		final double dt = toRadians(p2.getLatitude() - p1.getLatitude());
		final double dl = toRadians(p2.getLongitude() - p1.getLongitude());

		final double x = dl * Math.cos((t1 + t2) / 2);
		final double y = dt;
		return sqrt(x * x + y * y) * R;
	}
}
