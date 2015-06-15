package de.lemo.dms.db.interfaces;

public interface IActivity {
	
	public long getId();
	public String getInfo();
	public IContext getLearningContext();
	public IObject getLearningObject();
	public IPerson getPerson();
	public IActivity getReference();
	public Long getTime();
	

}
