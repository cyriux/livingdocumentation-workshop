/**
 * 
 */
package flottio.fuelcardmonitoring.domain;

import java.util.Collections;
import java.util.List;

import flottio.annotations.CoreConcept;
import flottio.annotations.GuidedTour;
import flottio.annotations.ValueObject;

/**
 * The fuel card monitoring report for one transaction, with a status and any
 * potential issue found.
 */
@ValueObject
@CoreConcept
@GuidedTour(name = "Quick Developer Tour", description = "The report for an incoming fuel card transaction", rank = 5)
public class FuelCardTransactionReport {

	/**
	 * The status of the monitoring report
	 */
	public enum MonitoringStatus {
		VERIFIED, WARNING, ANOMALY;
	}

	private final FuelCardTransaction transaction;
	private final MonitoringStatus status;
	private final List<String> issues;

	public FuelCardTransactionReport(FuelCardTransaction transaction, MonitoringStatus status, List<String> issues) {
		this.transaction = transaction;
		this.status = status;
		this.issues = issues;
	}

	public FuelCardTransaction getTransaction() {
		return transaction;
	}

	public MonitoringStatus getStatus() {
		return status;
	}

	public List<String> getIssues() {
		return Collections.unmodifiableList(issues);
	}

	@Override
	public int hashCode() {
		return issues.hashCode() ^ transaction.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof FuelCardTransactionReport)) {
			return false;
		}
		final FuelCardTransactionReport other = (FuelCardTransactionReport) obj;
		return status == other.status && transaction.equals(other.transaction) && issues.equals(other.issues);
	}

	@Override
	public String toString() {
		return "FuelCardTransactionReport [" + transaction.getDate() + ", status=" + status + ", " + issues.size()
				+ " issues]";
	}

}
