/**
 * File ./src/main/java/de/lemo/dms/service/ServiceLoginAuthentification.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

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
import de.lemo.dms.db.mapping.User;
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

		final Criteria criteria = session.createCriteria(User.class, "users");
		criteria.add(Restrictions.eq("users.authentication", loginHash));

		logger.debug("Looking for user:" + login);

		@SuppressWarnings("unchecked")
		ArrayList<User> results = (ArrayList<User>) criteria.list();

		ResultListLongObject res;

		if (!results.isEmpty()) {
			logger.debug("User found id:" + results.get(0).getId());
			ArrayList<Long> l = new ArrayList<Long>();
			l.add(0L);
			l.add(results.get(0).getId());
			res = new ResultListLongObject(l);
		} else {
			logger.debug("User " + login + " not found!");
			res = new ResultListLongObject();
		}
		session.close();
		return res;
	}

}