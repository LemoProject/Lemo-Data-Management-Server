package de.lemo.dms.connectors.clix2010;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.clix2010.clixDBClass.EComponent;
import de.lemo.dms.connectors.clix2010.clixHelper.TimeConverter;
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
			
			ClixImporter.getClixData();
			/*
	        Session session = HibernateUtil.getDynamicSourceDBFactoryClix(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
	        List<EComponent> ilm;
	        Criteria criteria = session.createCriteria(EComponent.class, "log");
			ilm = criteria.list();
			for(int i = 0; i < ilm.size(); i++)
			{
				EComponent ec = ilm.get(i);
				Long f = TimeConverter.getTimestamp(ec.getLastUpdated());
				if(i==0)
					System.out.println(f);
			}
			
	        session.close();*/
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
		try{
			ClixImporter.getClixData();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateData(long fromTimestamp) {
		try{
			ClixImporter.updateClixData(fromTimestamp);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
