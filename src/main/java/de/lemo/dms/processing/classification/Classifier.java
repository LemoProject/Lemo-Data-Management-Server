package de.lemo.dms.processing.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import de.lemo.dms.processing.questions.QDatabase;
import de.lemo.dms.processing.resulttype.UserInstance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
public class Classifier {

	private int numAttributes;
	private Instances instances;
	private List<UserInstance> userInstances;

	public Classifier() {
		userInstances = new QDatabase().queryAllUserInstances();
		setUserInstances(userInstances);
		createWekaData(userInstances);
		crossValidateClassifier();
		//trainClassifier();
		//saveArff();
	}

	private void saveArff() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("/home/vk/Desktop/UserInstances.arff"));
			writer.write(instances.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void crossValidateClassifier() {

		NaiveBayes tree = new NaiveBayes();         // new instance of tree
		try {
			//tree.buildClassifier(instances);   // build classifier
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(tree, instances, 3, new Random(1));
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
		NaiveBayes tree = new NaiveBayes(); 
		try {
			tree.buildClassifier(instances);
			for (int i = 0; i < 100; i++) {
				double pred = tree.classifyInstance(instances.instance(i));
				System.out.print("ID: " + i);
				System.out.print(", actual: " + instances.classAttribute().value((int) instances.instance(i).classValue()));
				System.out.println(", predicted: " + instances.classAttribute().value((int) pred));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createWekaData(List<UserInstance> userInstances) {
		FastVector      atts;

		// 1. set up attributes
		atts = defineAttributes();
		numAttributes = atts.size();
		// 2. create Instances object
		instances = new Instances("UserInstances", atts, 0);
		// 3. fill with data		
		for(UserInstance userInstance : userInstances){
			instances.add(createWekaInstance(userInstance));			
		}
		instances.setClassIndex(instances.numAttributes() - 1);
		// 4. output data
		System.out.println("Class attribute: " + instances.numAttributes());		
	}
	private Instance createWekaInstance(UserInstance userInstance) {
		double[] vals = new double[numAttributes];
		// - numeric
		vals[0] = userInstance.getDownVotes();
		vals[1] = userInstance.getImageCount();
		vals[2] = userInstance.getLinkCount();
		vals[3] = userInstance.getUpVotes();
		vals[4] = userInstance.getWordCount();
		vals[5] = userInstance.getProgressPercentage();
		vals[6] = userInstance.getClassId();

		return new Instance(1.0, vals);
	}

	private FastVector defineAttributes() {
		FastVector atts = new FastVector();

		atts.addElement(new Attribute("DownVotes"));
		atts.addElement(new Attribute("ImageCount"));
		atts.addElement(new Attribute("LinkCount"));
		atts.addElement(new Attribute("UpVotes"));
		atts.addElement(new Attribute("WordCount"));
		atts.addElement(new Attribute("Progress"));

		FastVector classId = new FastVector();
		classId.addElement("failed");
		classId.addElement("passed");
		atts.addElement(new Attribute("ClassId", classId));

		return atts;
	}

	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public List<UserInstance> getUserInstances() {
		return userInstances;
	}

	public void setUserInstances(List<UserInstance> userInstances) {
		this.userInstances = userInstances;
	}
}