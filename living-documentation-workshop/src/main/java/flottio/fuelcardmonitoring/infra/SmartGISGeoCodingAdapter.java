package flottio.fuelcardmonitoring.infra;

import flottio.annotations.ExternalActor;
import flottio.fuelcardmonitoring.domain.Coordinates;
import flottio.fuelcardmonitoring.domain.Geocoding;

@ExternalActor(name = "Google Geocoding", direction = ExternalActor.Direction.SPI)
//@EndPoint(ref = EndPoint.GOOGLE_GEOCODING)
public class SmartGISGeoCodingAdapter implements Geocoding {

	public Coordinates toCoordinates(String address) {
		// TODO Auto-generated method stub
		return null;
	}

}
