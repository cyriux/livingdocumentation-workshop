/**
 * 
 */
package flottio.livingdocumentation.metamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * A meta-model of the whole Information System, from a big picture perspective.
 * This meta-model is also expected to help for the overal testing strategy
 */
public class InformationSystemMetaModel {

	/**
	 * End-User Application -> Front Service Web Application Mobile Application
	 * Desktop Application
	 * 
	 * Front Service -> Back End Service
	 * 
	 * Back End Service Java -> Mainframe -> DB2 NoSQL Stores like Elastic
	 * Search <- event sync Java -> RDBMS
	 */

	public static InformationSystem describeInformationSystem() {
		InformationSystem system = new InformationSystem();

		DataStore db2 = new DataStore();
		db2.name = "DB2 Home Insurance";
		db2.technology = Technology.DB2;

		DataStore oracle = new DataStore();
		db2.name = "Oracle Life Insurance";
		db2.technology = Technology.ORACLE;
		
		//etc. TODO

		// system.services.add(e)
		return system;
	}

	public static interface Service {
	}

	public static class InformationSystem {
		public String name;
		public List<EndUserApp> services = new ArrayList<EndUserApp>();
	}

	public static abstract class Component {
		public String name;
		public Technology technology;
	}

	public static class EndUserApp extends Component {
		public List<FrontService> services = new ArrayList<FrontService>();
	}

	public static class FrontService extends Component implements Service {
		public List<BackEndService> services = new ArrayList<BackEndService>();
	}

	public static class BackEndService extends Component implements Service {
		public List<DataStore> services = new ArrayList<DataStore>();
	}

	public static class DataStore extends Component implements Service {
	}
	
	
	
	

}
