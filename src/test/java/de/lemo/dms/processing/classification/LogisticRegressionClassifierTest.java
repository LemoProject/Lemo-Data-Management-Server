package de.lemo.dms.processing.classification;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;


public class LogisticRegressionClassifierTest {
	private LogisticRegressionClassifier classifier;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		classifier = new LogisticRegressionClassifier();
	}
	
	UserInstance generateRandomUserInstance(){
		UserInstance userInstance = new UserInstance();
		userInstance.setAnswerCount((int) (Math.random()*10));
		userInstance.setClassId(Math.random()>0.5?1:0);
		userInstance.setCommentCount((int) (Math.random()*10));
		userInstance.setDownVotes((int) (Math.random()*10));
		userInstance.setUpVotes((int) (Math.random()*20));
		userInstance.setWordCount((int) (Math.random()*10));
		userInstance.setLinkCount((int) (Math.random()*10));
		userInstance.setProgressPercentage((int) (Math.random()*100));
		userInstance.setForumUsed(true);
		return userInstance;
	}
	
	
	@Test
	public void trainAndTestUserInstances(){
		List<UserInstance> userInstances = new ArrayList<UserInstance>();
		for(int i=0;i<10;i++){
			userInstances.add(generateRandomUserInstance());
		}		
		ResultListUserInstance result = classifier.trainAndTestUserInstances(userInstances, userInstances);
		assertNotNull(result.getClassifier());		
	}
	
	@Test
	public void trainAndTestValidation(){
		List<UserInstance> userInstances = new ArrayList<UserInstance>();
		for(int i=0;i<10;i++){
			userInstances.add(generateRandomUserInstance());
		}		
		ResultListUserInstance result = classifier.trainAndTestUserInstances(userInstances, userInstances);
		Double resultValue = null;
		try {
			JSONObject jSONResult = new JSONObject(result.getValidation());
			resultValue = Double.valueOf(jSONResult.get("Recall Positive").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(resultValue);
	}
}
