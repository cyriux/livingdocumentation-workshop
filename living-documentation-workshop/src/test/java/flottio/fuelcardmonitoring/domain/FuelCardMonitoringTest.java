package flottio.fuelcardmonitoring.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class FuelCardMonitoringTest {

	@Test
	public void testName() throws Exception {
		List<String> l = new ArrayList<String>();
		l.add(null);
		Iterator<String> it = l.iterator();
		while (it.hasNext()) {
			if (it.next() == null) {
				it.remove();
			}
		}
		assertEquals(0, l.size());
	}
}
