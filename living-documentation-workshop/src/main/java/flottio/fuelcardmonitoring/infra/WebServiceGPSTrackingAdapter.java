package flottio.fuelcardmonitoring.infra;

import java.util.Date;

import flottio.annotations.ExternalActor;
import flottio.annotations.ExternalActor.Direction;
import flottio.fuelcardmonitoring.domain.Coordinates;
import flottio.fuelcardmonitoring.domain.GPSTrackingGateway;

@ExternalActor(name = "GPS Tracking from Garmin", direction = Direction.SPI)
public class WebServiceGPSTrackingAdapter implements GPSTrackingGateway {

	public Coordinates locationAt(String id, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
