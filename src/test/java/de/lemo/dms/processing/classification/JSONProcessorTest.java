package de.lemo.dms.processing.classification;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import weka.classifiers.Evaluation;
import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;

public class JSONProcessorTest {

	private JSONProcessor jSONProcessor;
	private Classifier classifier;
	private Evaluation evaluation;
	private ResultListUserInstance result;
	
	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		ClassifierTest classifierTest = new ClassifierTest();
		classifier = new Classifier();
		List<UserInstance> userInstances = new ArrayList<UserInstance>();
		for(int i=0;i<10;i++){
			userInstances.add(classifierTest.generateRandomUserInstance());
		}		
		result = classifier.trainAndTestUserInstances(userInstances, userInstances);
	}
	
	@Test
	public void createValidationAccuracy(){
		String resultString = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultString = resultJSON.get("Accuracy").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultString)>0);
	}
	@Test
	public void createValidationPrecision(){
		String resultString = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultString = resultJSON.get("Precision").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultString)>=0 && Double.valueOf(resultString)<=1);
	}
	@Test
	public void createValidationRecall(){
		String resultString = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultString = resultJSON.get("Recall").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultString)>=0&& Double.valueOf(resultString)<=1);
	}
	@Test
	public void createValidationRecallPositiveNegative(){
		String resultPositive = null;
		String resultNegative = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultPositive = resultJSON.get("Recall Positive").toString();
			resultNegative = resultJSON.get("Recall Negative").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultPositive)+Double.valueOf(resultNegative)>0.99);
	}
	@Test
	public void createValidationROC(){
		String resultString = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultString = resultJSON.get("ROC Area").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultString)>0);
	}	
	@Test
	public void createValidationKappa(){
		String resultString = null;
		try {
			JSONObject resultJSON = new JSONObject(result.getValidation());
			resultString = resultJSON.get("Kappa").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(Double.valueOf(resultString)>0);
	}
}
