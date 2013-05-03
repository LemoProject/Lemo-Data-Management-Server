/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/abstractions/ICourseLORelation.java
 * Date 2013-03-14
 * Project Lemo Learning Analytics
 */


package de.lemo.dms.db.miningDBclass.abstractions;

import de.lemo.dms.db.miningDBclass.CourseMining;

/**
 * Interface for all Course-LearningObject-Association-Classes
 * @author Sebastian Schwarzrock
 */
public interface ICourseLORelation {
	
	long getId();
	
	CourseMining getCourse();
	
	ILearningObject getLearningObject();
	

}
