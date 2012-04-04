package de.lemo.dms.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import de.lemo.dms.db.QueryRunner;
import de.lemo.dms.processing.Query;


/**
 * A test service that should return some example data from a database.
 * 
 * @author Leonard Kappe
 * 
 */
@Path("/query")
public class ServiceQueryTest {

	@GET
	@Produces("text/html")
	public String startTimeHtml() {
		QueryRunner queryRunner = new QueryRunner();

		ResultSet queryResult = null;
		try {
			queryResult = queryRunner.run(new Query("SELECT name FROM users"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String response = "<html><title>Query Test</title><body><ul>";
		try {
			while (queryResult.next()) {
				response += "<li>" + queryResult.getNString("") + "</li>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response += "</ul></body></html>";

		return response;
	}
}
