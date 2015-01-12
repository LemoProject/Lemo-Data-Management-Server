package de.lemo.dms.processing.features;

import java.util.List;

import de.lemo.dms.db.mapping.LearningAttribute;

public class ContentWordCount extends ContentProcessor{


	public ContentWordCount() {
		super("Content_Wordcount");
	}

	@Override
	protected void processContent(List<LearningAttribute> contentAttributes) {
		for(LearningAttribute learningAttribute : contentAttributes){
			String trim = learningAttribute.getValue().trim();
			if (!trim.isEmpty()){
				learningAttribute.setValue(Integer.toString(trim.split("\\s+").length));
			}			
		}
	}
}

