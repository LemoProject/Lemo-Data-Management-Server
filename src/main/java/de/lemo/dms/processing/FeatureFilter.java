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
}
