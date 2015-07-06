/**
 * 
 */
package flottio.dispatching;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The optimization of the assignment of all resources over time to minimize
 * cost according to various constraints. The input is a schedule of requested
 * tasks, and the output is the planning as a collection of resources (vehicles,
 * fuel cards) assignments over a period of time.
 */
public class DispatchingOptimizationService {

	private FCAssignController controller;
	private VehicleAssignmentEntity vehicleAssignmentEntity;
	
	public Map<String, String> perform(Map<String, String> inputTable) {
		final HashMap<String, String> map = new HashMap<String, String>();
		int index = 0;
		index = forEachDriverCell(inputTable, map, index);
		return map;
	}

	// Need a lot of comments here cause the optimization is totally generic and is hard to read
	// TODO add more documentation here!
	public int forEachDriverCell(Map<String, String> inputTable, final HashMap<String, String> map, int index) {
		for (Entry<String, String> entry : inputTable.entrySet()) {
			final String key = entry.getKey();
			final String value = entry.getValue();
			if (key.startsWith("DRIVER_")) {
				map.put(key + index, value);
			}
			index++;
		}
		return index;
	}

	public FCAssignController getController() {
		return controller;
	}

	public void setController(FCAssignController controller) {
		this.controller = controller;
	}

	public VehicleAssignmentEntity getVehicleAssignmentEntity() {
		return vehicleAssignmentEntity;
	}

	public void setVehicleAssignmentEntity(VehicleAssignmentEntity vehicleAssignmentEntity) {
		this.vehicleAssignmentEntity = vehicleAssignmentEntity;
	}
	
	

}
