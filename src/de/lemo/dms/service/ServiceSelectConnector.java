package de.lemo.dms.service;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import de.lemo.dms.core.ConnectorManager;
import de.lemo.dms.db.ESourcePlatform;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * rest service to select a connector for the the data import
 * @author Boris Wenzlaff
 *
 */
@Path("/selectconnector")
public class ServiceSelectConnector extends ServiceBaseService {
	
	/**
	 * @param connector name of the collector for the data import
	 * @return name of the collector is the selection correct, otherwise returns "none"
	 */
	@GET @Produces("application/json")
	public SCConnectors selectConnectorJson(@QueryParam("connector") String connector) {
		super.logger.info("call for service: selectConnectorJson");
		ESourcePlatform c;
		SCConnectors scc = new SCConnectors();
		List<String> rsl = new ArrayList<String>();
		try  {
			c = ESourcePlatform.valueOf(connector);
			ConnectorManager cm = ConnectorManager.getInstance();
			cm.selectConnector(c);
			rsl.add(c.name());
			scc.setConnectors(rsl);
		}
		catch(IllegalArgumentException e) {
			rsl.add("none");
			scc.setConnectors(rsl);
		}
		catch(NullPointerException e) {
			rsl.add("none");
			scc.setConnectors(rsl);
		}
		return scc;
	}
}
