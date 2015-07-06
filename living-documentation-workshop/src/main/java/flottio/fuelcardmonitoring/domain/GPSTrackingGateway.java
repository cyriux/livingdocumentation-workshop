/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import java.util.Date;

import flottio.annotations.Repository;

/**
 * The GPS Tracking service that provides history of drivers locations (using
 * their smartphone) or vehicle locations (using on-board equipment) by date.
 */
@Repository
public interface GPSTrackingGateway {

	Coordinates locationAt(String id, Date date);
}
