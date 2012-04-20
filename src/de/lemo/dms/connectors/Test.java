package de.lemo.dms.connectors;

import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.connectors.moodle.ConnectorMoodle;


public class Test {
	
	public static void main(String[] args)
	{
		DBConfigObject sourceConf = new DBConfigObject();

		sourceConf.addProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		sourceConf.addProperty("hibernate.connection.url", "jdbc:mysql://localhost/lmsmoodle");
		sourceConf.addProperty("hibernate.connection.username", "datamining");
		sourceConf.addProperty("hibernate.connection.password", "LabDat1#");
		
		sourceConf.addProperty("hibernate.c3p0.min_size", "5");
		sourceConf.addProperty("hibernate.c3p0.max_size", "20");		
		sourceConf.addProperty("hibernate.c3p0.timeout", "300");
		sourceConf.addProperty("hibernate.c3p0.max_statements", "50");
		sourceConf.addProperty("hibernate.c3p0.idle_test_period", "3000");
		
		sourceConf.addProperty("hibernate.cache.use_second_level_cache", "false");		
		
		//sourceConf.addProperty("hibernate.show_sql", "true");
		//sourceConf.addProperty("hibernate.format_sql", "true");
		//sourceConf.addProperty("hibernate.use_sql_comments", "true");
		
		sourceConf.addProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		
		ConnectorMoodle cm = new ConnectorMoodle();
				
		cm.setSourceDBConfig(sourceConf);
		cm.getData();
	}
	
	
}
