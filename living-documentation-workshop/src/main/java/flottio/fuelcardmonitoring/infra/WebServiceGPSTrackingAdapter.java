package flottio.fuelcardmonitoring.infra;

import static flottio.annotations.ExternalActor.ActorType.SYSTEM;

import java.util.Date;

import flottio.annotations.ExternalActor;
import flottio.annotations.ExternalActor.Direction;
import flottio.fuelcardmonitoring.domain.Coordinates;
import flottio.fuelcardmonitoring.domain.LocationTracking;

@ExternalActor(name = "Garmin GPS Tracking Gateway", type = SYSTEM, direction = Direction.SPI)
public class WebServiceGPSTrackingAdapter implements LocationTracking {

	public Coordinates locationAt(String id, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
