/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import java.util.Date;

import flottio.annotations.Codex;
import flottio.annotations.Repository;

/**
 * Location Tracking provides history of drivers locations (using
 * their smartphone) or vehicle locations (using on-board equipment) by date.
 */
@Repository(rationale = Codex.SINGLE_GOLDEN_SOURCE)
public interface LocationTracking {

	Coordinates locationAt(String id, Date date);
}
