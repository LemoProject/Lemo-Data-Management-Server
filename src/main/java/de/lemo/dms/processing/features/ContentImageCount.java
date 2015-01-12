package de.lemo.dms.processing.features;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.lemo.dms.db.mapping.LearningAttribute;

public class ContentImageCount extends ContentProcessor {

	public ContentImageCount() {
		super("Content_Imagecount");
	}

	@Override
	protected void processContent(List<LearningAttribute> contentAttributes) {
		String regexp = generateRegExp();
		Pattern pattern = Pattern.compile(regexp);
		int counter;
		for(LearningAttribute learningAttribute : contentAttributes){
			counter = 0;
			Matcher matcher = pattern.matcher(learningAttribute.getValue());
			while (matcher.find()){
				counter++;				
			}
			learningAttribute.setValue(Integer.toString(counter));
		}
	}

	public String generateRegExp() {
		String regexp4 = "[^.\\s]+\\.(png|bmp|gif|jpg|pdf)";
		return regexp4;
	}

}
