package flottio.fuelcardmonitoring.infra;

import static flottio.annotations.ExternalActor.ActorType.SYSTEM;
import flottio.annotations.ExternalActor;
import flottio.fuelcardmonitoring.domain.Coordinates;
import flottio.fuelcardmonitoring.domain.Geocoding;

@ExternalActor(name = "Google Geocoding Provider", type = SYSTEM, direction = ExternalActor.Direction.SPI)
// @EndPoint(ref = EndPoint.GOOGLE_GEOCODING)
public class SmartGISGeoCodingAdapter implements Geocoding {

	public Coordinates toCoordinates(String address) {
		// TODO Auto-generated method stub
		return null;
	}

}
