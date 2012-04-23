package de.lemo.dms.core;

import com.sun.jersey.api.core.PackagesResourceConfig;

import de.lemo.dms.processing.Question;
import de.lemo.dms.service.BaseService;

/**
 * Resource config for the DMS, used to discover (and autoload) webservices in
 * different locations.
 * 
 * @author Leonard Kappe
 * 
 */
public class DMSResourceConfig extends PackagesResourceConfig {

    public DMSResourceConfig() {
        super(BaseService.class.getPackage().getName(), Question.class.getPackage().getName());
    }

}
