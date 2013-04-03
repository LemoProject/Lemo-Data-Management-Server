/**
 * File ./main/java/de/lemo/dms/service/ServiceRoles.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.resulttype.ResultListLongObject;

/**
 * Service for the authentification of an user
 */
@Path("authentification")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceLoginAuthentification {

	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	public ResultListLongObject authentificateUser(@QueryParam(MetaParam.USER_NAME) String login) {

		final IDBHandler dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		final Session session = dbHandler.getMiningSession();

		final String loginHash = Encoder.createMD5(login);

		final Criteria criteria = session.createCriteria(UserMining.class, "users");
		criteria.add(Restrictions.eq("users.login", loginHash));

		logger.info("Looking for user:" + login);

		@SuppressWarnings("unchecked")
		ArrayList<UserMining> results = (ArrayList<UserMining>) criteria.list();

		ResultListLongObject res;

		if (!results.isEmpty()) {
			logger.info("User found id:" + results.get(0).getId());
			ArrayList<Long> l = new ArrayList<Long>();
			l.add(0L);
			l.add(results.get(0).getId());
			res = new ResultListLongObject(l);
		} else {
			logger.info("User " + login + " not found!");
			res = new ResultListLongObject();
		}
		session.close();
		return res;
	}

}