package de.lemo.dms.processing.features;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mchange.util.AssertException;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.LearningAttribute;


public class PostRatingTest {
	
	private PostRating postRating;
	private List<LearningAttribute> learningAttributes;
	
	@Before
	public void setUp() {
		ServerConfiguration.getInstance().loadConfig("/lemo");
		postRating = new PostRating();
		learningAttributes = new ArrayList<LearningAttribute>();
	}

	@Test
	public void initialize() {

		assertTrue(postRating.getLearningAttributes().isEmpty());
	}
	
	@Test
	public void processLogs(){
		postRating.process();
		postRating.getLearningAttributes();
		learningAttributes = postRating.getLearningAttributes();
		assertTrue(learningAttributes.size()>0);		
	}
	
	@Test
	public void distinctObjectsInLearningAttributes(){
		boolean duplicates=false;
		for (int j=0;j<learningAttributes.size();j++)
		  for (int k=j+1;k<learningAttributes.size();k++)
		    if (k!=j && learningAttributes.get(j).getLearning().equals(learningAttributes.get(k).getLearning())){
		    	duplicates=true;
		    }		      
		assertFalse(duplicates);
	}
	
	@Test
	public void distinctLearningAttributesIds(){
		boolean duplicates=false;
		for (int j=0;j<learningAttributes.size();j++)
		  for (int k=j+1;k<learningAttributes.size();k++)
		    if (k!=j && learningAttributes.get(j).getId()==learningAttributes.get(k).getId()){
		    	duplicates=true;
		    }		      
		assertFalse(duplicates);
	}
	
	@Test
	public void uniqueAttribute(){
		boolean identical=true;
		for (int j=0;j<learningAttributes.size();j++)
		  for (int k=j+1;k<learningAttributes.size();k++)
		    if (k!=j && !learningAttributes.get(j).getAttribute().equals(learningAttributes.get(k).getAttribute())){
		    	identical=false;
		    }		      
		assertTrue(identical);
	}
}
