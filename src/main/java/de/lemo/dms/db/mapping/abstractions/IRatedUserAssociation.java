package de.lemo.dms.db.mapping.abstractions;

import de.lemo.dms.db.mapping.CourseMining;
import de.lemo.dms.db.mapping.UserMining;


/**
 * Interface for the association between the user and a rated object
 * @author Sebastian Schwarzrock
 */
public interface IRatedUserAssociation {

	CourseMining getCourse();
	
	UserMining getUser();
	
	Double getFinalGrade();
	
	Long getPrefix();
	
	Long getLearnObjId();
	
	Double getMaxGrade();

}
