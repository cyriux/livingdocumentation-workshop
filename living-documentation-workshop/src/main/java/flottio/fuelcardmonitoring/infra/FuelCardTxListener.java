package flottio.fuelcardmonitoring.infra;

import static flottio.annotations.ExternalActor.ActorType.SYSTEM;
import flottio.annotations.ExternalActor;
import flottio.annotations.ExternalActor.Direction;
import flottio.annotations.GuidedTour;
import flottio.dispatching.VehicleAssignmentEJB;
import flottio.fuelcardmonitoring.domain.FuelCardMonitoring;
import flottio.fuelcardmonitoring.domain.FuelCardTransaction;
import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport;
import flottio.fuelcardmonitoring.domain.Vehicle;
import flottio.fuelcardmonitoring.legacy.VehicleDatastore;

/**
 * Listens to incoming fuel card transactions from the external system of the
 * Fuel Card Provider
 */
@ExternalActor(name = "Fuelo Fuel Card Provider", type = SYSTEM, direction = Direction.API)
@GuidedTour(name = "Quick Developer Tour", description = "The MQ listener which triggers a full chain of processing", rank = 1)
public class FuelCardTxListener {

	private final FuelCardMonitoring monitoring;
	private VehicleAssignmentEJB dispatching = null;
	private VehicleDatastore vehicleDB;
	private ReportDAO dao;

	public FuelCardTxListener(FuelCardMonitoring monitoring) {
		this.monitoring = monitoring;
		this.dispatching = lookup();
	}

	private VehicleAssignmentEJB lookup() {
		// dummy impl.
		return new VehicleAssignmentEJB();
	}

	// @Transactional
	public void onMessage(Object msg) {
		FuelCardTransaction tx = deserialize(msg);
		final String cardId = tx.getCard().getId();
		final String driverId = dispatching.getDriverIdFor(cardId);
		final int vehicleId = dispatching.getVehicleForDriver(driverId, tx.getDate());
		final int tankSize = vehicleDB.getTankSize(vehicleId);

		final FuelCardTransactionReport report = monitoring.monitor(tx, new Vehicle(vehicleId, tankSize));
		dao.save(report);
	}

	private FuelCardTransaction deserialize(Object msg) {
		// dummy impl
		return (FuelCardTransaction) msg;
	}

}
