package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.LearningObj;
import de.lemo.dms.db.mapping.User;


/**
 * Interface for the association between the user and a rated object
 * @author Sebastian Schwarzrock
 */
public interface ILearningUserAssociation {

	Course getCourse();
	
	User getUser();
	
	Double getFinalGrade();
	
	LearningObj getLearning();

}
