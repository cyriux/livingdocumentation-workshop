/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import flottio.annotations.Repository;

/**
 * The geocoding service to convert a text address into global coordinates
 */
@Repository
public interface Geocoding {

	Coordinates toCoordinates(String address);
}
