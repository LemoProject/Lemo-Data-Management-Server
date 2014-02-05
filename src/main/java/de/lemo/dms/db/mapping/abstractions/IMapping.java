package de.lemo.dms.db.mapping.abstractions;


public interface IMapping {

	long getId();
	
	void setId(long id);

	boolean equals(IMapping o);
	
}
