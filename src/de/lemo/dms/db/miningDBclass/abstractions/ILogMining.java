package de.lemo.dms.db.miningDBclass.abstractions;

import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.UserMining;

public interface ILogMining {

    long getId();

    long getTimestamp();

    UserMining getUser();

    CourseMining getCourse();

    String getAction();
    
    String getTitle();
    
    Long getLearnObjId();
	
	public long getDuration();

}
