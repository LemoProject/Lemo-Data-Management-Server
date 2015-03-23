package de.lemo.dms.processing.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.apache.log4j.Logger;

import de.lemo.dms.processing.questions.QDatabase;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;

public class Classifier {

	private int numAttributes;
	private Instances instances;
	private List<UserInstance> userInstancesTraining;
	private J48 classifier;
	private List<UserInstance> userInstancesTesting;
	private final Logger logger = Logger.getLogger(this.getClass());
	private Evaluation evaluation;


	public Classifier() {
	}

	public ResultListUserInstance trainAndTestUserInstances(
			List<UserInstance> trainInstances, List<UserInstance> testInstances) {
		userInstancesTraining = trainInstances;
		userInstancesTesting = testInstances;
		setUserInstancesTraining(userInstancesTraining);
		createWekaData(userInstancesTraining);
		trainClassifier();
		applyClassifier();
		saveArff();
		ResultListUserInstance result = new ResultListUserInstance(getUserInstancesTesting());
		try {
			result.setClassifier(new JSONProcessor().createGraph(classifier.graph()).toString());
		} catch (Exception e) {
			logger.error("JSONObject of classifier can't be generated.", e);
		}
		result.setValidation(new JSONProcessor().createValidation(evaluation));
		return result;
	}
	
	private void applyClassifier() {
		createWekaData(userInstancesTesting);
		try {
			evaluation = new Evaluation(instances);
			evaluation.evaluateModel(classifier, instances);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double predictedClass;
		for(int i=0; i < instances.numInstances(); i++){
			try {
				predictedClass=classifier.classifyInstance(instances.instance(i));
				userInstancesTesting.get(i).setClassId((int)predictedClass);
			} catch (Exception e) {
				logger.error("Applying classifier to instances failed.", e);
			}
		}
	}

	private void saveArff() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("/home/vk/Desktop/UserInstances_vs_2.arff"));
			writer.write(instances.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error("Saving instances to arff-file failed.",e);
		}
	}

	private void crossValidateClassifier() {

		classifier = new J48();         // new instance of tree
		try {
			//classifier.buildClassifier(instances);   // build classifier
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(classifier, instances, 3, new Random(1));
			System.out.println("Instances: " +instances.numInstances());
			System.out.println("Correct classified: "+eval.correct());
			System.out.println("Percentage correct classified: "+eval.pctCorrect());
			System.out.println("Kappa: "+eval.kappa());
			System.out.println("Precision: "+eval.precision(1));
			System.out.println("Recall: "+eval.recall(1));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     // set the options
	}

	private void trainClassifier(){
		int minNumObj = 2;
		do{
		classifier = new J48();
		classifier.setMinNumObj(minNumObj);
		try {
			classifier.buildClassifier(instances);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		minNumObj *= 2;
		logger.info("Tree size: " + classifier.measureTreeSize());
		} while(classifier.measureTreeSize()>19);
	}

	//Creates for each userInstance a corresponding weka instance including class label.
	private void createWekaData(List<UserInstance> userInstances) {
		FastVector atts;
		atts = defineAttributes();
		numAttributes = atts.size();
		instances = new Instances("UserInstances", atts, 0);		
		for(UserInstance userInstance : userInstances){
			instances.add(createWekaInstance(userInstance));			
		}
		instances.setClassIndex(instances.numAttributes() - 1);
		logger.info("Class "+instances.relationName()+" attribute count: " + instances.numAttributes());		
	}

	//Transforms the user instance object into a weka instance using double array
	private Instance createWekaInstance(UserInstance userInstance) {
		double[] vals = new double[numAttributes];
		// - numeric
		vals[0] = userInstance.getDownVotes();
		vals[1] = userInstance.getImageCount();
		vals[2] = userInstance.getLinkCount();
		vals[3] = userInstance.getUpVotes();
		vals[4] = userInstance.getWordCount();
		vals[5] = userInstance.getReceivedUpVotes();
		vals[6] = userInstance.getReceivedDownVotes();
		vals[7] = userInstance.isForumUsed()?0:1;
		vals[8] = userInstance.getAnswerCount();
		vals[9] = userInstance.getCommentCount();
		vals[10] = userInstance.getPostCount();
		vals[11] = userInstance.getPostRatingSum();
		vals[12] = userInstance.getPostRatingMin();
		vals[13] = userInstance.getPostRatingMax();
/*		vals[14] = userInstance.getProgressPercentage();
		vals[15] = userInstance.getUnitProgress();
		vals[16] = userInstance.getLessonProgress();*/
		vals[14] = userInstance.getClassId();

		return new Instance(1.0, vals);
	}

	private FastVector defineAttributes() {
		FastVector atts = new FastVector();

		atts.addElement(new Attribute("DownVotes"));
		atts.addElement(new Attribute("ImageCount"));
		atts.addElement(new Attribute("LinkCount"));
		atts.addElement(new Attribute("UpVotes"));
		atts.addElement(new Attribute("WordCount"));
		atts.addElement(new Attribute("ReceivedUpVotes"));
		atts.addElement(new Attribute("ReceivedDownVotes"));

		FastVector forumActivity = new FastVector();
		forumActivity.addElement("active");
		forumActivity.addElement("passive");
		atts.addElement(new Attribute("ForumParticipation", forumActivity));

		atts.addElement(new Attribute("AnswerCount"));
		atts.addElement(new Attribute("CommentCount"));
		atts.addElement(new Attribute("PostCount"));
		atts.addElement(new Attribute("PostRatingSum"));
		atts.addElement(new Attribute("PostRatingMin"));
		atts.addElement(new Attribute("PostRatingMax"));
/*		atts.addElement(new Attribute("ProgressPercentage"));
		atts.addElement(new Attribute("UnitProgress"));
		atts.addElement(new Attribute("LessonProgress"));*/
		
		FastVector classId = new FastVector();
		classId.addElement("failed");
		classId.addElement("passed");
		atts.addElement(new Attribute("ClassId", classId));

		return atts;
	}

	public List<UserInstance> getUserInstancesTraining() {
		return userInstancesTraining;
	}

	public void setUserInstancesTraining(List<UserInstance> userInstances) {
		this.userInstancesTraining = userInstances;
	}
	
	public List<UserInstance> getUserInstancesTesting() {
		return userInstancesTesting;
	}

	public void setUserInstancesTesting(List<UserInstance> userInstancesTesting) {
		this.userInstancesTesting = userInstancesTesting;
	}

}