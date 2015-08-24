package flottio.fuelcardmonitoring.infra;

import java.util.Date;
import java.util.List;

import flottio.annotations.SightSeeingTour;
import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport;

@SightSeeingTour(name = "Quick Developer Tour", description = "The DAO to store the fuel card reports for an incoming fuel card transaction", rank = 7)
public class ReportDAO {

	public void save(FuelCardTransactionReport report) {
		// TODO Auto-generated method stub

	}

	public List<FuelCardTransactionReport> findAll(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}
