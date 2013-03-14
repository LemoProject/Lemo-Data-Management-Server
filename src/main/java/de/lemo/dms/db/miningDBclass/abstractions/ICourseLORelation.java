package de.lemo.dms.db.miningDBclass.abstractions;

/**
 * Interface for all Course-LearningObject-Association-Classes
 * @author Sebastian Schwarzrock
 */
public interface ICourseLORelation {
	
	long getId();
	
	Long getCourseId();
	
	Long getLearningObjectId();
	

}
