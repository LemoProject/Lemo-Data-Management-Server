package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.processing.resulttype.UserInstance;

//Collection of filters to preselect instances for classification.
public class FeatureFilter {

	public List<UserInstance> removeEnrolledButNotActive(List<UserInstance> userInstances){
		List<UserInstance> filteredUserInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(userInstance.getUnitProgress()>0){
				filteredUserInstances.add(userInstance);
			}
		}
		return filteredUserInstances;		
	}
	
	//Removes instances where progress_percentage is measured but no segment progress.
	public List<UserInstance> removeProgressWithoutSegments(List<UserInstance> userInstances){
		List<UserInstance> filteredInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(!(userInstance.getUnitProgress()==0 && userInstance.getProgressPercentage()>0)){
				filteredInstances.add(userInstance);
			}
		}
		return filteredInstances;
	}
	
	//Calculates the class using 80% of the maximum completed segments.
	//A problem could be users making old assessments.
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
				//System.out.println("Segments: "+ userInstance.getSegmentProgress());
			} else {
				userInstance.setClassId(0);
			}
		}		
		return userInstances;	
	}
}
