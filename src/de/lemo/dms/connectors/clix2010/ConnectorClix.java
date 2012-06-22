package de.lemo.dms.connectors.clix2010;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.HibernateUtil;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;

public class ConnectorClix implements IConnector{

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testConnections() {
		try{
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        List<EComponent> ilm;
	        Criteria criteria = session.createCriteria(EComponent.class, "log");
			ilm = criteria.list();
			System.out.println("!!!!!!!!!!!!!!!! - " + ilm.size());
	        session.close();
	        //HibernateDBHandler target= new HibernateDBHandler();
	        //target.getConnection(miningDBConf);
	        //target.closeConnection();
		}catch(HibernateException he)
		{
			he.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void getData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateData(long fromTimestamp) {
		// TODO Auto-generated method stub
		
	}

}
