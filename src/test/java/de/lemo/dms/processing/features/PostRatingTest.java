package de.lemo.dms.processing.features;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;


public class PostRatingTest {
	
	private PostRating postRating;
	
	@Before
	public void setUp() {
		ServerConfiguration.getInstance().loadConfig("/lemo");
		postRating = new PostRating();
	}

	@Test
	public void courseSize() {

		postRating.processLogs();
		//assertNotNull(result);
		//assertEquals(1247L, result.getElements().size());
	}
}
