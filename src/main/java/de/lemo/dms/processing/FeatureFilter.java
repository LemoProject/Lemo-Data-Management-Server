package de.lemo.dms.processing;

import java.util.ArrayList;
import java.util.List;

import de.lemo.dms.processing.resulttype.UserInstance;

//Collection of filters to preselect instances for classification.
public class FeatureFilter {

	public List<UserInstance> removeEnrolledButNotActive(List<UserInstance> userInstances){
		List<UserInstance> filteredUserInstances = new ArrayList<UserInstance>();
		for(UserInstance userInstance : userInstances){
			if(userInstance.getSegmentProgress()>0){
				filteredUserInstances.add(userInstance);
			}
		}
		return filteredUserInstances;		
	}
	
	//Calculates the class using 80% of the maximum completed segments.
	//A problem could be users making old assessments.
	public List<UserInstance> calculateClassValue(List<UserInstance> userInstances){
		int max = 0;
		for(UserInstance userInstance : userInstances){
			if(userInstance.getSegmentProgress()>max){
				max = userInstance.getSegmentProgress();
			}
		}
		double passingThreshold = (double)max/100*80;
		System.out.println("Passing Threshold: " + passingThreshold);
		for(UserInstance userInstance : userInstances){
			if(userInstance.getSegmentProgress()>passingThreshold){
				userInstance.setClassId(1);
				//System.out.println("Segments: "+ userInstance.getSegmentProgress());
			} else {
				userInstance.setClassId(0);
			}
		}		
		return userInstances;	
	}
}
