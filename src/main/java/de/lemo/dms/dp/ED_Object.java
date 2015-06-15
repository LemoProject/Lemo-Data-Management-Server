package de.lemo.dms.dp;



import java.util.List;

public interface ED_Object {
	
	public String getName();
	
	public ED_Object getParentObject();
	
	public List<ED_Object> getChildren();
	
	public String getType();
	
}
