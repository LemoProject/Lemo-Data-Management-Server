package de.lemo.dms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;


import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.processing.Query;

/**
 * 
 * Manages database connections and handles execution of queries.
 * 
 * @author Leonard Kappe
 */
public class QueryRunner {

    private static PoolableConnectionFactory connectionFactory;
    private Logger log = ServerConfigurationHardCoded.getInstance().getLogger();

    public static void setupDriver(String connectionURI, String userName, String password) throws Exception {
        connectionFactory = new PoolableConnectionFactory(new DriverManagerConnectionFactory(connectionURI, userName,
                password), new GenericObjectPool<Object>(null), null, null, false, true);

        // Create the PoolingDriver
        Class.forName("org.apache.commons.dbcp.PoolingDriver");
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.registerPool("example", connectionFactory.getPool());
    }

    public static void close() throws SQLException {
        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
        driver.closePool("example");
    }

    public ResultSet run(Query query) throws SQLException {
        log.info("Running query " + query);

        DataSource dataSource = new PoolingDataSource(connectionFactory.getPool());
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query.getSqlQuery());
            log.info("Query " + query + " excecuted");

            int j = 0;
            int numcols = result.getMetaData().getColumnCount();
            while (result.next()) {
                for (int i = 1; i <= numcols; i++) {
                    log.debug("Row: " + j + ", Column: " + i + ", Result: " + result.getString(i));
                }
                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (result != null)
                result.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }
        return result;
    }

}
