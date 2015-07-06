package flottio.fuelcardmonitoring.monitoring;

import flottio.annotations.DomainService;

/**
 * The JMX monitoring bean for monitoring
 */
@DomainService
public class FuelCardJMXBean {
	
	private final static FuelCardJMXBean INSTANCE  = new FuelCardJMXBean();

	public String onRequest(){
		return null;
	}
}
