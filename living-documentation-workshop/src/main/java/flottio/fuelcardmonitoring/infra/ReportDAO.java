package flottio.fuelcardmonitoring.infra;

import java.util.Date;
import java.util.List;

import flottio.annotations.ExternalActor;
import flottio.annotations.ExternalActor.ActorType;
import flottio.annotations.ExternalActor.Direction;
import flottio.annotations.GuidedTour;
import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport;

@ExternalActor(name = "Monitoring via DB polling", type = ActorType.SYSTEM, direction = Direction.API)
@GuidedTour(name = "Quick Developer Tour", description = "The DAO to store the resulting fuel card reports after processing", rank = 7)
public class ReportDAO {

	public void save(FuelCardTransactionReport report) {
		// TODO Auto-generated method stub

	}

	public List<FuelCardTransactionReport> findAll(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
