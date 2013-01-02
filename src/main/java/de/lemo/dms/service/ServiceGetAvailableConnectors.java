package de.lemo.dms.service;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;

import de.lemo.dms.connectors.ConnectorManager;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.service.servicecontainer.SCConnectors;

/**
 * REST Webservice for the available connectors of the DMS
 * 
 * @author Boris Wenzlaff
 * 
 */

@Path("/getavailableconnectors")
public class ServiceGetAvailableConnectors extends BaseService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SCConnectors getAvailableConnecttorsJson() {
        super.logger.info("call for service: getAvailableConnecttorsJson");
        SCConnectors rs = new SCConnectors();
        rs.setConnectors(getConnectors());
        return rs;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getAvailableConnecttorsHtml() {
        super.logger.info("call for service: getAvailableConnecttorsHtml");
        StringBuilder result = new StringBuilder();
        result.append("<html><title>Available Connectors</title><body><h2>Available Connectors</h2><ul>");
        for(String s : getConnectors()) {
            result.append("<li>").append(s).append("</li>");
        }
        result.append("</ul></body></html>");
        return result.toString();
    }

    private List<String> getConnectors() {
        List<String> result = Lists.newArrayList();
        for(IConnector connector : ConnectorManager.getInstance().getAvailableConnectors()) {
            result.add(connector.toString());
        }
        return result;

    }
}
