package lemo.dms.processing;

/**
 * Generic question based on a SQL query statement.
 * 
 * @author Leonard Kappe
 */
public class Query {

	private String statement;

	public Query(String statement) {
		this.statement = statement;
	}

	public String getSqlQuery() {
		return statement;
	}

}
