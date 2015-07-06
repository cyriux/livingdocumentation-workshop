/**
 * 
 */
package flottio.fuelcardmonitoring.infra.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import flottio.fuelcardmonitoring.domain.FuelCardTransactionReport;
import flottio.fuelcardmonitoring.infra.ReportDAO;

/**
 * The REST resource
 */
public class ReportResource {

	// @XmlSerializable etc.
	// DTO for Jackson
	public class Report {
		private String status;
		private String transactionId;
		private List<String> issues;
	}

	private ReportDAO dao;

	// GET etc.
	public List<Report> get(Date date) {
		List<FuelCardTransactionReport> reports = dao.findAll(date);
		final List<Report> jsonReports = new ArrayList<Report>();
		for (FuelCardTransactionReport report : reports) {
			jsonReports.add(translate(report));
		}
		return jsonReports;
	}

	private Report translate(FuelCardTransactionReport report) {
		// TODO Auto-generated method stub
		return null;
	}
}
