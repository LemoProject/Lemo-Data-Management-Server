package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.User;


/**
 * Interface for the association between the user and a rated object
 * @author Sebastian Schwarzrock
 */
public interface IRatedUserAssociation {

	Course getCourse();
	
	User getUser();
	
	Double getFinalGrade();
	
	Long getLearnObjId();
	
	Double getMaxGrade();

}
