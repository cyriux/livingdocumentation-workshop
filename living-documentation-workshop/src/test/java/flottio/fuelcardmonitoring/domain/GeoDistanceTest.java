package flottio.fuelcardmonitoring.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import flottio.fuelcardmonitoring.domain.Coordinates;
import flottio.fuelcardmonitoring.domain.GeoDistance;

public class GeoDistanceTest {

	private final Coordinates CENTRE_POMPIDOU = new Coordinates(48.8608333, 2.3516667);

	private final Coordinates EIFFEL_TOWER = new Coordinates(48.858222, 2.2945);

	@Test
	public void distance_zero_between_two_identical_points() throws Exception {
		assertEquals(0, GeoDistance.EQUIRECTANGULAR.distanceBetween(CENTRE_POMPIDOU, CENTRE_POMPIDOU), 0.1);
	}

	@Test
	public void distance_4km2_between_centre_pompidou_and_Eiffel_Tower() throws Exception {
		assertEquals(4190, GeoDistance.EQUIRECTANGULAR.distanceBetween(CENTRE_POMPIDOU, EIFFEL_TOWER), 10);
	}
}
