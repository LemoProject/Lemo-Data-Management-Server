package de.lemo.dms.processing.classification;

import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import weka.classifiers.Evaluation;

/* 
 * Processes the string results of the classifier and returns JSONObjects.
 */
public class JSONProcessor {
	public JSONObject createGraph(String graph) {
		String graphSplit[] = graph.replace("'","").split("\\r?\\n");
		JSONObject decisionTree = new JSONObject();
		JSONArray nodes = new JSONArray();
		JSONArray links = new JSONArray();
		int numberOfNodes = 0;

		for(int i=0; i<graphSplit.length;i++){
			try {
				if(!graphSplit[i].contains("->") && graphSplit[i].contains("label")){
					String lineSplit[] = graphSplit[i].split("\"");
					JSONObject name = new JSONObject().put("name", lineSplit[1]);
					if(lineSplit[1].contains("(")){
						String valueSplit[] = lineSplit[1].split(Pattern.quote("."));
						String value[] = valueSplit[0].split(Pattern.quote("("));
						name.put("value", value[1]);
					}
					nodes.put(numberOfNodes, name);
					numberOfNodes++;
				} 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<graphSplit.length;i++){
			try {
				if(graphSplit[i].contains("->")){
					String labelSplit[] = graphSplit[i].split("\"");
					String lineSplit[] = graphSplit[i].split("N");
					String source[] = lineSplit[1].split("-");
					String target[] = lineSplit[2].split(" ");
					
					JSONObject link = new JSONObject();
					link.put("source", Integer.valueOf(source[0]));
					link.put("target", Integer.valueOf(target[0]));
					link.put("name", labelSplit[1]);
					links.put(link);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int i= links.length()-1;i>=0;i--){
			try {
				JSONObject link = links.getJSONObject(i);
				JSONObject source = nodes.getJSONObject(link.getInt("source"));
				JSONObject target = nodes.getJSONObject(link.getInt("target"));
				int value = target.optInt("value");
				link.put("value", value);
				value += source.optInt("value");
				source.put("value", value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			decisionTree.put("nodes", nodes);
			decisionTree.put("links", links);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decisionTree;
	}

	public String createValidation(Evaluation evaluation) {
		JSONObject validation = new JSONObject();
		try {
			validation.put("Accuracy", evaluation.pctCorrect());
			validation.put("Precision", (evaluation.precision(0)+evaluation.precision(1))/2);
			validation.put("Recall", (evaluation.recall(0)+evaluation.recall(1))/2);
			validation.put("Recall Positive", evaluation.recall(0));
			validation.put("Recall Negative", evaluation.recall(1));
			validation.put("Kappa", evaluation.kappa());
			validation.put("ROC Area", evaluation.areaUnderROC(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return validation.toString();
	}

}
