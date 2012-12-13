package de.lemo.dms.core;

import java.io.File;
import java.io.FileReader;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.project.MavenProject;

/**
 * 
 * @author Boris Wenzlaff
 *
 */
public class Version {
	private final String pom = "pom.xml";

	/**
	 * read the version number from the pom.xml
	 * @return
	 */
	public String getServerVersion() {
		String version = "unknown";
		File pomfile = new File(pom);
		Model model = null;
		FileReader reader = null;
		MavenXpp3Reader mavenreader = new MavenXpp3Reader();
		try {
		    reader = new FileReader(pomfile);
		    model = mavenreader.read(reader);
		    model.setPomFile(pomfile);
		    version = model.getVersion();
		}catch(Exception ex){
			
		}
		return version;
	}
}
