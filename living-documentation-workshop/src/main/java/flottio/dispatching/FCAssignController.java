/**
 * 
 */
package flottio.dispatching;

import java.util.Map;

/**
 * The assignment of a fuel card to a driver or to a vehicle, with associated
 * conditions of use.
 */
public class FCAssignController {

	public Map<String, String> execute(VehicleAssignmentEntity vehicleAssignmentEntity){
		VehicleAssignmentEntity entity = vehicleAssignmentEntity.withX();
		return null;		
	}
}
