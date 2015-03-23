package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.lemo.dms.processing.resulttype.UserInstance;

/**
 * Collection of filters to preselect instances for classification.
 *
 */
public class FeatureFilter {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	public List<UserInstance> removeEnrolledButNotActive(List<UserInstance> userInstances){
		List<UserInstance> filteredUserInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(userInstance.getUnitProgress()>0){
				filteredUserInstances.add(userInstance);
			}
		}
		return filteredUserInstances;		
	}
	
	/**
	 * Removes instances where progress_percentage is measured but no segment progress.
	 * @param userInstances the list that should be filtered
	 * @return a filtered list of user instances
	 */
	public List<UserInstance> removeProgressWithoutSegments(List<UserInstance> userInstances){
		List<UserInstance> filteredInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(!(userInstance.getUnitProgress()==0 && userInstance.getProgressPercentage()>0)){
				filteredInstances.add(userInstance);
			}
		}
		return filteredInstances;
	}
	
	/**
	 * Calculates the class label using 80% of the maximum completed segments.
	 * A problem could be users making old assessments.
	 * @param userInstances
	 * @return
	 */
	public List<UserInstance> calculateClassValue(List<UserInstance> userInstances){
		int minimumProgressForPassing = Integer.MAX_VALUE;
		for(UserInstance userInstance : userInstances){
			if(userInstance.getProgressPercentage()==100 && userInstance.getUnitProgress()>0 && userInstance.getUnitProgress()<minimumProgressForPassing){
				minimumProgressForPassing = userInstance.getUnitProgress();
			}
		}
		double passingThreshold = (double)minimumProgressForPassing/100*80;
		System.out.println("Passing Threshold: " + passingThreshold);
		for(UserInstance userInstance : userInstances){
			if(userInstance.getUnitProgress()>passingThreshold){
				userInstance.setClassId(1);
			} else {
				userInstance.setClassId(0);
			}
		}		
		return userInstances;	
	}
	
	public List<UserInstance> removeInstructors(List<UserInstance> userInstances){
		List<UserInstance> filteredInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(userInstance.getRoleId()!=null && userInstance.getRoleId().equals(3L)){
				filteredInstances.add(userInstance);
			} else{
				logger.info("Instructor removed. UserId: "+userInstance.getUserId());
			}
		}
		return filteredInstances;		
	}
}
