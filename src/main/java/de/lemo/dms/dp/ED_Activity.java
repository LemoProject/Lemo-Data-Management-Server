package de.lemo.dms.dp;


public interface ED_Activity {
	
	public ED_Person getAPerson();
	
	public ED_Context getContext();
	
	public ED_Object getObject();
	
	public Long getTime();
	
	public String getAction();
	
	public String getInfo();
	
	public ED_Activity getRef();
	
}
