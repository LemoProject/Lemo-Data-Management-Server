/**
 * File ./main/java/de/lemo/dms/service/ServiceRoles.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.processing.resulttype.ResultListLongObject;
@Path("authentification")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceLoginAuthentification extends BaseService {

	@GET
	public ResultListLongObject authentificateUser(String login) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();
		
		final String loginHash = Encoder.createMD5(login);

		final Criteria criteria = session.createCriteria(UserMining.class, "users");
		criteria.add(Restrictions.eq("users.login", loginHash));
		
		ArrayList<UserMining> results = (ArrayList<UserMining>) criteria.list();

		ResultListLongObject res;
		
		if(results != null && results.size() > 0)
		{
			ArrayList<Long> l = new ArrayList<Long>();
			l.add(results.get(0).getId());
			res = new ResultListLongObject(l);
		}
		else
			res = new ResultListLongObject();
		return res;
	}

}